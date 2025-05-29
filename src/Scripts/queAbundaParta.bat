@echo off
setlocal
set "URL=https://i.ytimg.com/vi/ZhswkOVWn7o/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDYngoO398vgSeOtxYGGK1YsAJxvA"
set "DESTINO=%~dp0imagem.jpg"
curl -L -o "%DESTINO%" "%URL%"
start "" "%DESTINO%"
endlocal