package com.irenyescobar.mytasksapp.repositories.exceptions

import java.lang.Exception

class SaveTaskRemoteError(cause: Throwable): Exception("Ocorreu um erro ao salvar os dados na base remota.", cause)