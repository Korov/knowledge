# Set Environments In Windows

## Check Environment By Name

```powershell
[18:19:31] ~ on ❯  [System.Environment]::GetEnvironmentVariable("JAVA_HOME","Machine")
C:\Users\korov\Desktop\install\jdk\default
```

`"JAVA_HOME"` is the name of environment, `"Machine"` means system level, `"User"` means user level.

```powershell
[18:26:47] ~ on ❯  $Env:JAVA_HOME
C:\Users\korov\Desktop\install\jdk\default
```

you can also get environment value by `$Env:JAVA_HOME`.

## Create permanent environment variables

```powershell
[System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Users\korov\Desktop\install\jdk\graalvm-java11", "Machine")
```

If the environment variable exist, new environment variable will represent the old one.