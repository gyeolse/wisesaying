import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.seyefactory.wisespoon.data.model.WiseSaying
import com.seyefactory.wisespoon.ui.component.CustomTopAppBar
import com.seyefactory.wisespoon.ui.navigation.ScreenInfo
import com.seyefactory.wisespoon.ui.theme.AndroidSampleTheme
import com.seyefactory.wisespoon.ui.viewmodel.WiseSayingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()
) {
    AndroidSampleTheme {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = "검색",
                    onBackClick = { navController.popBackStack() }
                )
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background,
            ) {
                AutoCompleteWiseSaying(viewModel = wiseSayingViewModel, navController = navController)
            }
        }
    }
}

@Composable
fun AutoCompleteWiseSaying(
    viewModel: WiseSayingViewModel,
    navController: NavController
) {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 검색 입력 필드
        SearchBar(
            text = text,
            onTextChange = {
                text = it
                viewModel.searchWiseSayings(it)
                expanded = it.isNotEmpty()
            },
            onClearText = { text = "" },
            onCancel = { expanded = false }
        )

        if (expanded && viewModel.searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(viewModel.searchResults) { wiseSaying ->
                    QuoteItem(
                        wiseSaying = wiseSaying,
                        onClick = {
                            navController.navigate("${ScreenInfo.Home.route}/${wiseSaying.uid}") {
                                Log.d(
                                    "Navigation",
                                    "Navigating to: ${ScreenInfo.Home.route}/${wiseSaying.uid}"
                                )

                                popUpTo(ScreenInfo.Home.route) {
                                    inclusive = true // HomeScreen 중복 방지
                                    saveState = false // 상태 저장 필요 없음
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        } else if (expanded && viewModel.searchResults.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center, // 세로 중앙 정렬
                horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙 정렬
            ) {
                Text(
                    text = "검색된 명언이 없어요.", fontSize = 18.sp, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = { navController.navigate(ScreenInfo.Home.route) },
                    modifier = Modifier.padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, // 버튼 배경색 설정
                        contentColor = Color.White   // 텍스트 색상 설정
                    )
                ) {
                    Text("명언 보러가기 👉")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onClearText: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F5F5), // 검색창 배경색
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 검색 입력 필드
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
                .height(36.dp), // 텍스트 필드 높이 설정
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp), // 내부 패딩 조정
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = "검색어를 입력하세요",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                    innerTextField() // 필드 내부의 텍스트를 표시
                }
            }
        )

        // 'X' 버튼
        if (text.isNotEmpty()) {
            IconButton(onClick = onClearText) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "텍스트 삭제",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun QuoteItem(
    wiseSaying: WiseSaying,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .background(Color(0xFFF8F8F8), shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = wiseSaying.contents,
            fontSize = 18.sp, fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = wiseSaying.author,
            fontSize = 18.sp, fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}