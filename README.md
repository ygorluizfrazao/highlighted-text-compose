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

