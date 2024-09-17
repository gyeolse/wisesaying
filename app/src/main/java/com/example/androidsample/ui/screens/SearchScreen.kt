import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.ui.navigation.ScreenInfo
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel

@Composable
fun SearchScreen(navController: NavController,
                 wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()) {
    AndroidSampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AutoCompleteWiseSaying(wiseSayingViewModel, navController)
        }
    }
}

@Composable
fun AutoCompleteWiseSaying(viewModel: WiseSayingViewModel, navController: NavController) {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column {
        // TextField (사용자 입력)
        TextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.searchWiseSayings(it)  // 입력할 때마다 검색 수행
                expanded = it.isNotEmpty() // 입력이 있을 때만 리스트를 보여줌
            },
            label = { Text("Search wise sayings") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // 검색 결과 리스트
        if (expanded && viewModel.searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(viewModel.searchResults) { wiseSaying ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                text = wiseSaying.contents
                                navController.navigate("${ScreenInfo.Home.route}/${wiseSaying.uid}") {
                                    Log.d("Navigation", "Navigating to: ${ScreenInfo.Home.route}/${wiseSaying.uid}")

                                    // SearchScreen의 상태를 유지하지 않고 popUpTo를 HomeScreen으로 변경
                                    popUpTo(ScreenInfo.Home.route) {
                                        inclusive = true // HomeScreen이 이미 있을 경우 중복 방지
                                        saveState = false // 새로운 화면으로 이동 시 상태 저장 불필요
                                    }
                                    launchSingleTop = true
                                    restoreState = true                                }
                                expanded = false
                            }
                            .padding(8.dp)
                    ) {
                        HighlightedText(fullText = wiseSaying.contents, searchText = text)
                        HighlightedText(fullText = "Author: ${wiseSaying.author}", searchText = text)
                    }
                    Divider() // 항목 간 구분선
                }
            }
        }
    }
}

@Composable
fun HighlightedText(fullText: String, searchText: String) {
    val annotatedString = remember(fullText, searchText) {
        val builder = AnnotatedString.Builder()

        // 검색어가 존재할 경우 해당 부분을 Bold로 처리
        val startIndex = fullText.indexOf(searchText, ignoreCase = true)
        if (startIndex >= 0) {
            // 검색어 전 텍스트 추가
            builder.append(fullText.substring(0, startIndex))

            // 검색어 부분을 Bold로 처리
            builder.withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                builder.append(fullText.substring(startIndex, startIndex + searchText.length))
            }

            // 검색어 이후 텍스트 추가
            builder.append(fullText.substring(startIndex + searchText.length))
        } else {
            builder.append(fullText)  // 검색어가 없을 경우 전체 텍스트 추가
        }

        builder.toAnnotatedString()
    }

    Text(text = annotatedString)
}
