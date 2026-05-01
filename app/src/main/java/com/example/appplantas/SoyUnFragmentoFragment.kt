package com.example.appplantas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class SoyUnFragmentoFragment : Fragment() {

    private val TAG = "YouTubeDebug"
    private val viewModel: VideosViewModel by activityViewModels()
    private var isInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_soy_un_fragmento, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ytViews = listOf<YouTubePlayerView>(
            view.findViewById(R.id.youtube_player_1),
            view.findViewById(R.id.youtube_player_2),
            view.findViewById(R.id.youtube_player_3),
            view.findViewById(R.id.youtube_player_4)
        )

        // IMPORTANTE: NO agregamos el Observer aquí al inicio. 
        // En el S24+, añadir el observer dispara una inicialización interna que puede
        // chocar con nuestra carga secuencial y causar el error "Invalid video id".

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            if (!isInitialized) {
                isInitialized = true
                // Delay inicial para que la vista esté completamente "asentada"
                view.postDelayed({
                    if (isAdded) {
                        val ids = state.asList()
                        Log.d(TAG, "Iniciando secuencia de carga blindada con IDs: $ids")
                        startSequentialLoading(ytViews, ids, 0)
                    }
                }, 1000) 
            }
        }
    }

    private fun startSequentialLoading(views: List<YouTubePlayerView>, ids: List<String>, index: Int) {
        if (index >= views.size || !isAdded) {
            Log.d(TAG, "Secuencia de carga finalizada.")
            return
        }

        val playerView = views[index]
        val videoId = if (index < ids.size) ids[index] else ""

        if (videoId.length != 11) {
            Log.e(TAG, "ID INVÁLIDO en índice $index: '$videoId'. Saltando...")
            startSequentialLoading(views, ids, index + 1)
            return
        }

        Log.d(TAG, "--- Inicializando Player ${index + 1} ---")

        val iFrameOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .build()

        playerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (!isAdded) return
                
                Log.i(TAG, "✅ Player ${index + 1} listo. Cargando ID: $videoId")
                
                // Forzamos un pequeño retraso antes de enviar el comando de video 
                // para que el motor JS de YouTube esté totalmente estable.
                playerView.postDelayed({
                    if (isAdded) {
                        try {
                            youTubePlayer.cueVideo(videoId, 0f)
                            
                            // Vinculamos el ciclo de vida SOLO después de que el video 
                            // esté cargado para evitar errores de 'pauseVideo' prematuros.
                            viewLifecycleOwner.lifecycle.addObserver(playerView)
                            
                        } catch (e: Exception) {
                            Log.e(TAG, "Error al ejecutar cueVideo ${index + 1}: ${e.message}")
                        }
                    }
                }, 500)

                // Esperamos 2 segundos antes de iniciar el siguiente para no saturar al S24+
                playerView.postDelayed({
                    if (isAdded) startSequentialLoading(views, ids, index + 1)
                }, 2000)
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerError) {
                Log.e(TAG, "❌ Error en Player ${index + 1}: $error")
                // Intentamos con el siguiente a pesar del error
                startSequentialLoading(views, ids, index + 1)
            }
        }, iFrameOptions)
    }
}
