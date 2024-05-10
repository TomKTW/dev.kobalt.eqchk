package dev.kobalt.eqchk.android.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.main.MainColors
import dev.kobalt.eqchk.main.MainTheme
import eqchk.android.generated.resources.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    viewState: DetailsViewState?,
    onRefresh: () -> Unit = {},
) {
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = viewState?.isLoading == true,
        onRefresh = { onRefresh.invoke() }
    )
    Box(
        modifier = Modifier
            .pullRefresh(pullToRefreshState),
        contentAlignment = Alignment.TopCenter
    ) {
        viewState?.event?.let { event ->
            Column(
                modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            ) {
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_earthquake_24),
                    title = "Magnitude",
                    value = event.magnitude ?: "Unknown"
                )
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_south_24),
                    title = "Depth",
                    value = event.depth?.let { "$it km" } ?: "Unknown"
                )
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_access_time_24),
                    title = "Time",
                    value = listOfNotNull(
                        event.timeStamp,
                        event.timeAgo.let { "$it ago" }
                    ).takeIf { it.isNotEmpty() }?.joinToString("\n") ?: "Unknown"
                )
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_location_on_24),
                    title = "Location",
                    value = listOfNotNull(
                        event.location,
                        event.coordinates
                    ).joinToString("\n")
                )
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_readiness_score_24),
                    title = "Intensity",
                    value = listOfNotNull(
                        event.estimatedIntensityScale?.let { "Estimation: ${it.label}" },
                        event.communityIntensityScale?.let { "Reports: ${it.label}" }
                    ).takeIf { it.isNotEmpty() }?.joinToString("\n") ?: "No felt reports"
                )
                ImageTitleValueLabel(
                    image = painterResource(Res.drawable.ic_baseline_summarize_24),
                    title = "Summary",
                    value = (listOfNotNull(
                        event.tectonicSummary?.let { "Tectonic" },
                        event.impactSummary?.let { "Impact" }
                    ).takeIf { it.isNotEmpty() }?.joinToString(" and ") ?: "No") + " reports available",
                    onClick = {

                    }.takeIf { event.let { it.tectonicSummary != null || it.impactSummary != null } }
                )
            }
        }
        PullRefreshIndicator(
            viewState?.isLoading == true,
            pullToRefreshState
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageTitleValueLabel(
    image: Painter,
    title: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth().let { modifier ->
            onClick?.let { modifier.clickable { it.invoke() } } ?: modifier
        }.padding(12.dp)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(56.dp).padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Column(
            modifier = Modifier.weight(1f).padding(vertical = 6.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = MainColors.black.copy(alpha = 0.5f)
            )
        }
        if (onClick != null) Image(
            painter = painterResource(Res.drawable.ic_baseline_chevron_right_24),
            contentDescription = null,
            modifier = Modifier.size(56.dp).padding(vertical = 8.dp, horizontal = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
    Divider(
        color = Color.Black.copy(alpha = 0.1f),
        modifier = Modifier.height(1.dp).fillMaxWidth()
    )
}

@Preview
@Composable
fun DetailsContentPreview() {
    MainTheme {
        Surface {
            DetailsContent(
                viewState = DetailsViewState(
                    isLoading = false,
                    event = DetailsEventEntity(
                        EventEntity(
                            "ID",
                            location = "Location",
                            timestamp = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                            estimatedIntensity = BigDecimal.ONE,
                            communityIntensity = BigDecimal.ONE,
                            communityResponseCount = 10,
                            magnitude = BigDecimal.ONE,
                            latitude = 0.0,
                            longitude = 0.0,
                            depth = 0.0,
                            detailsUrl = "about:blank",
                            tectonicSummary = null,
                            impactSummary = null
                        )
                    )
                )
            )
        }
    }
}