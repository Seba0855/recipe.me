package pl.smcebi.recipeme.model.barcode.products

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import pl.smcebi.recipeme.model.barcode.products.ProductImage.FrontImage.Display

/**
 * Serializer that was created to overcome the issue with imageUrl that can be received under various locales.
 * For example - you won't receive a universal "imageUrl", but you will receive "pl", or "de", that cannot be deserialized easily.
 */
internal object ProductImageSerializer: KSerializer<Display> {
    private val mapSerializer = MapSerializer(String.serializer(), String.serializer())
    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun deserialize(decoder: Decoder): Display {
        val imagesMap = decoder.decodeSerializableValue(mapSerializer)
        return Display(imageUrl = imagesMap.values.firstOrNull())
    }

    override fun serialize(encoder: Encoder, value: Display) =
        mapSerializer.serialize(encoder, mapOf("" to value.imageUrl.orEmpty()))
}