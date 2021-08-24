package com.example.developerslife.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.developerslife.GifTypes
import com.example.developerslife.R
import com.example.developerslife.network.ApiRepository
import com.example.developerslife.network.RetrofitClient

private const val ARG_PARAM_TYPE = "type"

class GifFragment : Fragment(R.layout.fragment_gif) {

    private var imageUri: Uri? = null
    private var type: GifTypes? = null
    private val viewModel: MainViewModel = MainViewModel(ApiRepository(RetrofitClient.getClient()))

    private var count = 1
    val DOWNLOAD_COUNT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getParcelable(ARG_PARAM_TYPE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image: ImageView = view.findViewById(R.id.image)
        val left: ImageButton = view.findViewById(R.id.right)
        val right: ImageButton = view.findViewById(R.id.left)


        left.setOnClickListener {
            viewModel.setData(type ?: GifTypes.BEST, 2)
        }
        right.setOnClickListener {
            viewModel.setData(type ?: GifTypes.BEST, 0)
        }


        viewModel.setData(type ?: GifTypes.BEST, count % DOWNLOAD_COUNT)
        viewModel.gif.observe(viewLifecycleOwner, {
            it.handle {
                success { it1 ->
                    imageUri = it1.url.toUri()
                    if (imageUri != null) {
                        Glide.with(requireContext()).asGif()
                            .load(imageUri)
                            .into(image);
                        count++
                    }
                }
                loading {
                    Log.d("TAG", "loading")
                }
                error { code, s, _ ->
                    Log.d("TAG", "error")
                }
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: GifTypes) =
            GifFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_TYPE, param1)
                }
            }
    }
}