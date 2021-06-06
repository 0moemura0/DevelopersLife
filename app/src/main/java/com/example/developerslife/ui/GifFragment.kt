package com.example.developerslife.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class GifFragment : Fragment() {

    private var imageUri: Uri? = null
    private var type: GifTypes? = null
    private val viewModel: MainViewModel = MainViewModel(ApiRepository(RetrofitClient.getClient()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getParcelable(ARG_PARAM_TYPE)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gif, container, false)
        val image: ImageView = root.findViewById(R.id.image)
        val tyda: ImageButton = root.findViewById(R.id.tyda)
        val obratno: ImageButton = root.findViewById(R.id.obratno)
        tyda.setOnClickListener {
            viewModel.setData(type ?: GifTypes.BEST, 2)
        }
        obratno.setOnClickListener {
            viewModel.setData(type ?: GifTypes.BEST, 0)
        }
        viewModel.setData(type ?: GifTypes.BEST, 1)
        viewModel.gif.observe(viewLifecycleOwner, {
            it.handle {
                success { it1 ->
                    imageUri = it1.url.toUri()
                    if (imageUri != null) {
                        Glide.with(requireContext()).asGif()
                            .load(imageUri)
                            .into(image);
                    }
                }
                loading {
                    Log.d("NAG", "loading")
                }
                error { code, s, _ ->
                    Log.d("NAG", "error")
                    Log.d("NAG", code.toString())
                    Log.d("NAG", s)
                }
            }
        })
        return root
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