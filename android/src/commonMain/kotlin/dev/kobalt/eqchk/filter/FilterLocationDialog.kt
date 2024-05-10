package dev.kobalt.eqchk.android.filter

import androidx.compose.runtime.Composable

@Composable
fun FilterLocationDialog(
    currentRange: Int,
    currentCoordinates: Pair<Double, Double>,
    onSubmit: (range: Int, coordinates: Pair<Double, Double>) -> Unit,
    onClear: () -> Unit,
    onDismiss: () -> Unit
) {
    /*
    var mapCoordinates by remember { mutableStateOf(currentCoordinates) }
    var sliderPosition by remember { mutableFloatStateOf(currentRange.toFloat()) }
    var zoom by remember { mutableDoubleStateOf(1.0) }

    val mapListener = object : MapListener {
        override fun onScroll(event: ScrollEvent?): Boolean {
            event?.source?.mapCenter?.let { it.latitude to it.longitude }?.also { mapCoordinates = it }
            return true
        }

        override fun onZoom(event: ZoomEvent?): Boolean {
            event?.zoomLevel?.also { zoom = it }
            return true
        }
    }

    // Reference: https://gis.stackexchange.com/questions/7430/what-ratio-scales-do-google-maps-zoom-levels-correspond-to
    val metersPerPx = 156543.03392 * Math.cos(mapCoordinates.first * Math.PI / 180) / Math.pow(2.0, zoom)

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Location",
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Box(
                    modifier = Modifier
                        .aspectRatio(1.0f)
                        .clip(CircleShape)
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxWidth(),
                        factory = { context ->
                            HomeMapView(context).apply {
                                outlineProvider = ViewOutlineProvider.BACKGROUND
                                clipToOutline = true
                                removeMapListener(mapListener)
                                addMapListener(mapListener)
                                setExpectedCenter(GeoPoint(mapCoordinates.first, mapCoordinates.second))
                            }
                        }
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(
                                min(
                                    LocalConfiguration.current.screenWidthDp.dp,
                                    (sliderPosition / (metersPerPx / 1000)).toFloat().dp
                                )
                            )
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = CircleShape
                            )
                            .background(Color.Black.copy(alpha = 0.25f), CircleShape),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(Color.Black, CircleShape),
                        )
                    }

                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Row {
                    Text(
                        text = "${sliderPosition.toBigDecimal().setScale(0, RoundingMode.HALF_EVEN)} km",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = mapCoordinates.first.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
                            .toString() + ", " + mapCoordinates.second.toBigDecimal()
                            .setScale(2, RoundingMode.HALF_EVEN).toString(),
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }
                Slider(
                    value = sliderPosition,
                    steps = 100,
                    onValueChange = { range -> sliderPosition = range },
                    valueRange = 1f..1000f,
                    onValueChangeFinished = { }
                )
                Row {
                    TextButton(
                        onClick = { onDismiss.invoke() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onClear.invoke(); onDismiss.invoke() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Clear")
                    }
                    TextButton(
                        onClick = { onSubmit.invoke(sliderPosition.toInt(), mapCoordinates); onDismiss.invoke() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }*/
}