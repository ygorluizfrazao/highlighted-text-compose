package br.com.frazo.highlighted_text_compose

import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HighlightedText(
    text: String,
    highlightedSentences: List<String>,
    normalTextSpanStyle: SpanStyle,
    highlightedSentencesTextSpanStyle: SpanStyle,
    ignoreCase: Boolean = true,
    content: (@Composable (AnnotatedString) -> Unit)
) {
    content(
        highlightedText(
            text = text,
            highlightedSentences = highlightedSentences,
            normalTextSpanStyle = normalTextSpanStyle,
            highlightedSentencesTextSpanStyle = highlightedSentencesTextSpanStyle,
            ignoreCase = ignoreCase
        )
    )
}

@Composable
fun highlightedText(
    text: String,
    highlightedSentences: List<String>,
    normalTextSpanStyle: SpanStyle,
    highlightedSentencesTextSpanStyle: SpanStyle,
    ignoreCase: Boolean = true
): AnnotatedString {

    val highlightedSentencesFiltered =
        highlightedSentences.filter { it.trim().isNotBlank() }.distinct()

    var annotatedString by remember {
        mutableStateOf(AnnotatedString(text = text))
    }

    if (highlightedSentencesFiltered.isNotEmpty()) {
        LaunchedEffect(key1 = highlightedSentencesFiltered) {
            CoroutineScope(Dispatchers.Default).launch {
                val newAnnotation = highlightSequences(
                    sentences = highlightedSentencesFiltered,
                    text = text,
                    normalTextSpanStyle = normalTextSpanStyle,
                    highlightedSentencesTextSpanStyle = highlightedSentencesTextSpanStyle,
                    ignoreCase = ignoreCase
                )
                annotatedString = newAnnotation
            }
        }
    } else {
        annotatedString = buildAnnotatedString {
            withStyle(style = normalTextSpanStyle) {
                append(text)
            }
        }
    }

    return annotatedString
}

private fun highlightSequences(
    sentences: List<String>,
    text: String,
    normalTextSpanStyle: SpanStyle,
    highlightedSentencesTextSpanStyle: SpanStyle,
    ignoreCase: Boolean
): AnnotatedString {

    var auxString = buildAnnotatedString {
        withStyle(style = normalTextSpanStyle) {
            append(text)
        }
    }

    sentences.forEach { highlightString ->

        val normalStyleBuffer: StringBuilder = StringBuilder()
        val highLightStyleBuffer: StringBuilder = StringBuilder()

        auxString = buildAnnotatedString {

            var currentRange = (0..0)
            var lastAnnotationSizeAdded = 0
            var isInNormalStyle = true

            auxString.windowed(
                highlightString.length,
                step = 1,
                partialWindows = true
            ) { windowChars ->

                currentRange = (currentRange.last..currentRange.last + 1)

                if (lastAnnotationSizeAdded > 0) {
                    lastAnnotationSizeAdded--
                    return@windowed
                }

                if (windowChars.first().toString().isBlank()) {
                    if (!isInNormalStyle) {
                        withStyle(style = highlightedSentencesTextSpanStyle) {
                            pushStringAnnotation(
                                tag = highLightStyleBuffer.toString(),
                                annotation = highLightStyleBuffer.toString()
                            )
                            append(highLightStyleBuffer.toString())
                        }
                        isInNormalStyle = true
                        highLightStyleBuffer.clear()
                    }
                    normalStyleBuffer.append(windowChars.first())
                    return@windowed
                }

                val existingAnnotationsInRange =
                    auxString.getStringAnnotations(
                        currentRange.first,
                        currentRange.last
                    )

                if (existingAnnotationsInRange.isNotEmpty()) {
                    existingAnnotationsInRange.forEach { existingAnnotation ->
                        if (isInNormalStyle) {
                            withStyle(style = normalTextSpanStyle) {
                                append(normalStyleBuffer.toString())
                            }
                            isInNormalStyle = false
                            normalStyleBuffer.clear()
                        }
                        highLightStyleBuffer.append(existingAnnotation.item)
                        lastAnnotationSizeAdded += existingAnnotation.item.length
                    }
                    lastAnnotationSizeAdded -= 1
                    return@windowed
                }

                if ((ignoreCase && windowChars.toString()
                        .uppercase() == highlightString.uppercase())
                    || (!ignoreCase && windowChars.toString() == highlightString)
                ) {

                    if (isInNormalStyle) {
                        withStyle(style = normalTextSpanStyle) {
                            append(normalStyleBuffer.toString())
                        }
                        isInNormalStyle = false
                        normalStyleBuffer.clear()
                    }

                    highLightStyleBuffer.append(windowChars.toString())
                    lastAnnotationSizeAdded += windowChars.length - 1
                    return@windowed
                }

                if (!isInNormalStyle) {
                    withStyle(style = highlightedSentencesTextSpanStyle) {
                        pushStringAnnotation(
                            tag = highLightStyleBuffer.toString(),
                            annotation = highLightStyleBuffer.toString()
                        )
                        append(highLightStyleBuffer.toString())
                    }
                    isInNormalStyle = true
                    highLightStyleBuffer.clear()
                }
                normalStyleBuffer.append(windowChars.first())
            }
            withStyle(style = highlightedSentencesTextSpanStyle) {
                pushStringAnnotation(
                    tag = highLightStyleBuffer.toString(),
                    annotation = highLightStyleBuffer.toString()
                )
                append(highLightStyleBuffer.toString())
            }
            withStyle(style = normalTextSpanStyle) {
                append(normalStyleBuffer.toString())
            }
        }
    }

    return auxString
}