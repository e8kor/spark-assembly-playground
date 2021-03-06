val root = project.in(file("."))
  .settings(Settings.withCommon)
  .settings(Settings.withTesting)
  .settings(Settings.withAssembly)
  .settings(
    libraryDependencies ++=
      Settings.spark ++
        Settings.testing ++
        Settings.logging ++
        Settings.frameless ++
        Settings.notebook ++
        Settings.databases
  )