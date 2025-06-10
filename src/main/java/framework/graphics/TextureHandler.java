package framework.graphics;

import framework.application.IHandler;
import framework.injection.annotations.Singleton;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

@Singleton
public class TextureHandler implements IHandler{

    private final HashMap<String, Integer> textureMap = new HashMap<>();


    public int loadTexture(String path) {
        if (textureMap.containsKey(path))
            return textureMap.get(path);

        ByteBuffer buffer;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buffer = STBImage.stbi_load(new File(path).getAbsolutePath(), w, h, channels, 4);
            if (buffer == null)
                throw new Exception("Unable to load file " + path + " " + STBImage.stbi_failure_reason());

            int texture = GL11.glGenTextures();
            textureMap.put(path, texture);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w.get(), h.get(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            STBImage.stbi_image_free(buffer);
            return texture;
        } catch(Exception e) {
            e.printStackTrace();
        } return 0;
    }

}
