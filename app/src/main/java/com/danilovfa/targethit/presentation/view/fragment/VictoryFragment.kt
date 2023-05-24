package com.danilovfa.targethit.presentation.view.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentVictoryBinding
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.presentation.mapper.ScoreArgsMapper
import com.danilovfa.targethit.presentation.viewmodel.VictoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class VictoryFragment : Fragment() {

    private val binding: FragmentVictoryBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: VictoryViewModel by viewModels()
    private val args: VictoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val score = ScoreArgsMapper().fromEntity(args.score)

        binding.apply {
            textVictoryScore.text = resources.getString(R.string.victory_score, score.score)
            buttonVictory.setOnClickListener {
                onVictoryClick(score)
            }
            buttonShare.setOnClickListener {
                shareResults(score)
            }
        }
    }

    private fun onVictoryClick(score: Score) {
        if (!args.isCustom)
            saveScore(score)
        else
            navigateToLevels()
    }


    // Function to save a bitmap to a temporary file and get its URI
    private fun saveBitmapToFile(bitmap: Bitmap): Uri? {
        try {
            val imagesFolder = File(requireContext().getExternalFilesDir(null), "images")
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "image_results.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            return FileProvider.getUriForFile(requireContext(), "com.danilovfa.targethit.fileprovider", file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun shareResults(score: Score) {
        val image = viewModel.getImageResults(score, args.fieldWidth, args.fieldHeight)
        val imageUri = saveBitmapToFile(image)
        val text = resources.getString(R.string.share_text, score.score)
        if (imageUri != null) {
            ShareCompat.IntentBuilder(requireContext())
                .setType("image/*")
                .setSubject(text)
                .addStream(imageUri)
                .setChooserTitle("Share score")
                .startChooser()
        }
    }

    private fun saveScore(score: Score) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            val message = exception.message ?: resources.getString(R.string.victory_error_default)
            setError(message)
        }
        lifecycleScope.launch(coroutineExceptionHandler) {
            setLoader()
            withContext(Dispatchers.IO) {
                viewModel.setScore(score)
                viewModel.setLevelCompleted(score.levelId)
            }
            navigateToLevels()
        }
    }

    private fun setError(message: String) {
        binding.apply {
            victoryLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
        }
    }

    private fun setLoader() {
        binding.apply {
            victoryLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun navigateToLevels() {
        val action =
            VictoryFragmentDirections.actionVictoryFragmentToLevelsFragment(args.destination)
        findNavController().navigate(action)
    }
}