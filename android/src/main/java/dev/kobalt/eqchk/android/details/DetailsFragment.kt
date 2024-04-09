package dev.kobalt.eqchk.android.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.kobalt.eqchk.android.base.BaseFragment
import dev.kobalt.eqchk.android.databinding.DetailsBinding

@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailsBinding>() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load(args.uid)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.composeContainer?.setContent {
            MaterialTheme {
                val event: DetailsViewState? by viewModel.viewState.collectAsState(null)
                Surface {
                    DetailsScreen(
                        onBackButtonClick = {
                            navigateBack()
                        },
                        onOpenButtonClick = {
                            event?.detailsUri?.let { openInBrowser(it) }
                        },
                        event
                    )
                }
            }
        }
    }

    private fun openInBrowser(uri: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

}