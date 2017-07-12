package com.mrpowergamerbr.loritta.commands.vanilla.`fun`

import com.google.common.collect.ImmutableMap
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.LorittaUtils
import java.awt.image.BufferedImage
import java.util.*

class PretoEBrancoCommand : CommandBase() {
	override fun getLabel(): String {
		return "pretoebranco"
	}

	override fun getAliases(): List<String> {
		return listOf("preto&branco")
	}

	override fun getDescription(): String {
		return "Relembre os belos momentos ao imprimir trabalhos para a escola, quando suas belas imagens coloridas no Word viravam imagens irreconhecíveis em preto e branco, só porque não tinha tinta colorida!"
	}

	override fun getExample(): List<String> {
		return Arrays.asList("@Loritta")
	}

	override fun getDetailedUsage(): Map<String, String> {
		return ImmutableMap.builder<String, String>()
				.put("mensagem", "Usuário sortudo")
				.build()
	}

	override fun getCategory(): CommandCategory {
		return CommandCategory.FUN
	}

	override fun needsToUploadFiles(): Boolean {
		return true
	}

	override fun run(context: CommandContext) {
		val image = LorittaUtils.getImageFromContext(context, 0)

		if (!LorittaUtils.isValidImage(context, image)) { return }

		val blackAndWhite = BufferedImage(image.width, image.height, BufferedImage.TYPE_BYTE_GRAY)
		blackAndWhite.graphics.drawImage(image, 0, 0, null)

		context.sendFile(blackAndWhite, "pretoebranco.png", context.getAsMention(true))
	}
}