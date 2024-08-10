package com.haghpanh.pienote.features.home.ui.component

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.TextButton
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
import com.haghpanh.pienote.commonui.component.PienoteTextField
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@Composable
fun AddCategoryComponent(
    modifier: Modifier = Modifier,
    onAddNewCategory: (String, String?) -> Unit,
    onDiscard: () -> Unit
) {
    BackHandler(onBack = onDiscard)

    var categoryName by remember {
        mutableStateOf("")
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
            .imePadding()
            .clip(PienoteTheme.shapes.large)
            .background(PienoteTheme.colors.surfaceContainerHighest)
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = SelectingNoteOptions.AddCategory.label,
            style = PienoteTheme.typography.titleMedium,
            color = PienoteTheme.colors.onSurfaceVariant
        )

        HorizontalDivider()

        PienoteTextField(
            value = categoryName,
            onValueChange = { value -> categoryName = value },
            placeHolder = {
                Text(
                    text = "Unnamed",
                    style = PienoteTheme.typography.headlineSmall
                )
            },
            textStyle = PienoteTheme.typography.headlineSmall
        )

        AnimatedContent(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            targetState = categoryImage
        ) { imageUri ->
            if (imageUri == null) {
                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        pickMedia.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Text(text = stringResource(id = R.string.add_cover_image))
                }
            } else {
                Box(
                    modifier = Modifier
                        .clickable {
                            pickMedia.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                        .clip(PienoteTheme.shapes.small)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                ) {
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
                                        PienoteTheme.colors.surfaceContainerLowest
                                    )
                                )
                            )
                            .fillMaxWidth()
                            .aspectRatio(1f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    onAddNewCategory(
                        categoryName,
                        categoryImage?.toString()
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PienoteTheme.colors.tertiaryContainer,
                    contentColor = PienoteTheme.colors.onTertiaryContainer
                )
            ) {
                Text(text = "Add")
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
                Text(text = "Discard")
            }
        }
    }
}
