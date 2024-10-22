package pienote.features.home.ui.component

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.haghpanh.pienote.R
import pienote.commonui.component.PienoteTextField
import pienote.commonui.theme.PienoteTheme

@Composable
fun AddCategoryComponent(
    modifier: Modifier = Modifier,
    onAddNewCategory: (String, Uri?) -> Unit,
    onDiscard: () -> Unit
) {
    BackHandler(onBack = onDiscard)

    var categoryName: String? by remember {
        mutableStateOf(null)
    }
    var categoryImage: Uri? by remember {
        mutableStateOf(null)
    }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> categoryImage = uri }
    )

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .clip(PienoteTheme.shapes.large)
            .background(PienoteTheme.colors.surfaceContainerHighest)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = SelectingNoteOptions.AddCategory.label,
            style = PienoteTheme.typography.titleMedium,
            color = PienoteTheme.colors.onSurfaceVariant
        )

        HorizontalDivider()

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .clip(PienoteTheme.shapes.small)
                .background(PienoteTheme.colors.background)
                .clickable {
                    pickMedia.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            if (categoryImage == null) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart),
                    text = stringResource(id = R.string.add_cover_image),
                    style = PienoteTheme.typography.displayLarge,
                    color = PienoteTheme.colors.onBackground,
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .clickable {
                            pickMedia.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    model = categoryImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    PienoteTheme.colors.surfaceContainerHighest
                                )
                            )
                        )
                        .fillMaxWidth()
                        .aspectRatio(1f),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        PienoteTextField(
            modifier = modifier.fillMaxWidth(),
            value = categoryName.orEmpty(),
            onValueChange = { categoryName = it },
            placeHolderText = stringResource(R.string.label_unnamed),
            textStyle = PienoteTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (categoryName.isNullOrEmpty().not()) {
                        onAddNewCategory(
                            categoryName!!,
                            categoryImage
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PienoteTheme.colors.tertiaryContainer,
                    contentColor = PienoteTheme.colors.onTertiaryContainer
                )
            ) {
                Text(text = stringResource(R.string.label_add))
            }

            OutlinedButton(
                onClick = onDiscard,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PienoteTheme.colors.onErrorContainer
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = PienoteTheme.colors.onErrorContainer
                )
            ) {
                Text(text = stringResource(R.string.label_discard))
            }
        }
    }
}
