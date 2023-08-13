
rootProject.name = "X1-Kotlin"
include("main")
include("src:main")
findProject(":src:main")?.name = "main"
