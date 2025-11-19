Get-ChildItem -Directory e* | ForEach-Object {
    Write-Host "Checking $($_.Name)"
    $files = Get-ChildItem -Recurse $_.FullName -Filter *.java
    if ($files) {
        javac $files.FullName
        if ($LASTEXITCODE -ne 0) {
            Write-Host "Failed in $($_.Name)"
            exit 1
        } else {
            Write-Host "Success in $($_.Name)"
        }

        # Remove all generated class files in this folder
        $classFiles = Get-ChildItem -Recurse $_.FullName -Filter *.class
        if ($classFiles) {
            Remove-Item $classFiles.FullName -Force
        }
    }
}
