# highlighted-text-compose
Convenience composable functions to highlight bits of texts

<div id="header" align="center">
  <a href="https://jitpack.io/#ygorluizfrazao/composed-permissions"><img src="https://jitpack.io/v/ygorluizfrazao/easy-permissions.svg" alt="Version Name"/></a>
  <img src="https://komarev.com/ghpvc/?username=ygorluizfrazao&style=flat-square&color=blue" alt=""/>
</div>
<div id="badges" align="center">
  <a href="https://www.linkedin.com/in/ygorluizfrazao/">
    <img src="https://img.shields.io/badge/LinkedIn-blue?style=flat&logo=linkedin&logoColor=white" alt="LinkedIn Badge"/>
  </a>
  <a href="https://ko-fi.com/ygorfrazao">
    <img src="https://img.shields.io/badge/Kofi-blue?style=flat&logo=kofi&logoColor=white" alt="Youtube Badge"/>
  </a>
</div>

## How can i use it?

Just add this to your *settings.gradle*:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Then, in your *build.gradle*:

```groovy

```

## What it does?

Have you ever used `buildAnnotatedString` function? if yes, you know you will have a lot of rework for each spannable you want to build. This lib is a convenience to cover most use cases. For me, it was created to highlight sentences in a random text, like:

![image](https://user-images.githubusercontent.com/17025709/227956521-5647fcf4-e33c-4d3a-9d5c-cf2c3512f1d8.png)![image](https://user-images.githubusercontent.com/17025709/227956782-deca0765-4cc2-4e84-bc49-477a89ee57f1.png)

There are two functions that you can use to achieve that result:

```kotlin
@Composable
fun HighlightedText(
    text: String,
    highlightedSentences: List<String>,
    normalTextSpanStyle: SpanStyle,
    highlightedSentencesTextSpanStyle: SpanStyle,
    ignoreCase: Boolean = true,
    content: (@Composable (AnnotatedString) -> Unit)
)
```
Which will pass the result in a lambda Composable block.

And

```kotlin
@Composable
fun highlightedText(
    text: String,
    highlightedSentences: List<String>,
    normalTextSpanStyle: SpanStyle,
    highlightedSentencesTextSpanStyle: SpanStyle,
    ignoreCase: Boolean = true
): AnnotatedString 
```

Which will return an AnnotatedString.

In the above example, something like this was used:

```kotlin
HighlightedText(
    text = note.text,
    highlightedSentences = highlightSentences,
    normalTextSpanStyle = MaterialTheme.typography.bodyMedium.toSpanStyle(),
    highlightedSentencesTextSpanStyle = MaterialTheme.typography.bodyMedium.copy(
        color = LocalTextSelectionColors.current.handleColor,
        background = LocalTextSelectionColors.current.backgroundColor
    ).toSpanStyle()
) {
    Text(
        modifier = Modifier
            .padding(top = MaterialTheme.spacing.small),
        textAlign = TextAlign.Justify,
        text = it,
        style = MaterialTheme.typography.bodyMedium
    )
}
```

## Clickable Text
It is included a wraper to material3 ClickableText as well, as the submodule depedency:
```groovy

```
Can produce results like this:

![image](https://user-images.githubusercontent.com/17025709/227959923-2c95ff6a-0d02-496a-9720-7373385496a0.png)


with the signature:
```kotlin
@Composable
fun ReusableClickableText(
    modifier: Modifier = Modifier,
    text: String,
    clickableParts: Map<String, (String) -> Unit>,
    normalTextSpanStyle: SpanStyle,
    clickableTextSpanStyle: SpanStyle = normalTextSpanStyle.copy(color = Color.Blue)
)
```

In the above case, the function is used like this:

```kotlin
ReusableClickableText(
    modifier = Modifier.padding(top = MaterialTheme.spacing.small),
    text = "Click here to clear search query",
    clickableParts = mapOf("here" to { viewModel.clearFilter() }),
    normalTextSpanStyle = LocalTextStyle.current.toSpanStyle()
        .copy(color = LocalContentColor.current)
)
```

And, that's all, hope it can help you.

## Other notes
You can do whatever you see fancy with the code.
