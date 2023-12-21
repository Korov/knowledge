param (
    [string]$command = "help",
    [string]$candidate
)

function help() {
    Write-Host "Usage: sdk <command> [candidate] [version]"
}

function version() {
    Write-Host "0.0.1"
}

function list($candidate) {
    Write-Host "$candidate"
}

switch ($command) {
    "help" {
        help
    }
    "version" {
        version
    }
    "list" {
        list $candidate
    }
}