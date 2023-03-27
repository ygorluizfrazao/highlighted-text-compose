package br.com.frazo.reusable_clickable_text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import br.com.frazo.highlighted_text_compose.HighlightedText

@Composable
fun ReusableClickableText(
    modifier: Modifier = Modifier,
    text: String,
    clickableParts: Map<String, (String) -> Unit>,
    normalTextSpanStyle: SpanStyle,
    clickableTextSpanStyle: SpanStyle = normalTextSpanStyle.copy(color = Color.Blue)
) {
    HighlightedText(
        text = text,
        highlightedSentences = clickableParts.keys.toList(),
        normalTextSpanStyle = normalTextSpanStyle,
        highlightedSentencesTextSpanStyle = clickableTextSpanStyle
    ) {
        ClickableText(
            modifier = modifier,
            text = it,
            onClick = { offset ->
                it.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { span ->
                        clickableParts[span.tag]?.invoke(span.tag)
                    }
            })
    }
}