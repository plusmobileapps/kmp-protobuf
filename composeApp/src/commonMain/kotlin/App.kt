import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plusmobileapps.protobuf.Dog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import protobufsample.composeapp.generated.resources.Res
import protobufsample.composeapp.generated.resources.compose_multiplatform

sealed class UiState {
    data object Loading : UiState()
    data class Loaded(val dog: Dog) : UiState()
    data class Error(val exception: Throwable) : UiState()
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var state: UiState by remember { mutableStateOf(UiState.Loading) }
        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            scope.launch {
                DogApi.getDog()
                    .onSuccess { dog -> state = UiState.Loaded(dog) }
                    .onFailure { exception -> state = UiState.Error(exception) }
            }
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            when (val currentState = state) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Loaded -> {
                    Text("Name: ${currentState.dog.name}")
                    Text("Breed: ${currentState.dog.breed}")
                    Text("Birthday: ${currentState.dog.birthday}")
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = "Compose Multiplatform"
                    )
                }
                is UiState.Error -> {
                    Text("Error: ${currentState.exception.message}")
                    Button(onClick = {
                        state = UiState.Loading
                        scope.launch {
                            DogApi.getDog()
                                .onSuccess { dog -> state = UiState.Loaded(dog) }
                                .onFailure { exception -> state = UiState.Error(exception) }
                        }
                    }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}