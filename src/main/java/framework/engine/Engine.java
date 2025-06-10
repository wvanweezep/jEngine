package framework.engine;

import framework.application.GraphicApplication;
import framework.graphics.Mesh;
import framework.graphics.MeshHandler;
import framework.graphics.ShaderTextured;
import framework.graphics.TextureHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Engine extends GraphicApplication {

    float[] vertices = {-0.5f,-0.5f,0f,
            0.5f, -0.5f, 0f,
            0f,0.5f,0f};
    float[] texCoords = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.5f, 1.0f
    };
    int[] indices = {0,1,2};
    Mesh mesh;
    int texture;

    @Override
    protected void onStart() {
        System.out.println("Starting Application...");
        mesh = INJECTOR.get(MeshHandler.class).createMesh(vertices, texCoords, indices);
        texture = INJECTOR.get(TextureHandler.class).loadTexture("./resources/texture2.png");

        new ShaderTextured().use();
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onExit() {
        System.out.println("Exiting Application...");
    }

    @Override
    protected void onRender() {
        GL30.glBindVertexArray(mesh.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT,0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public static void main(String[] args) {
        new Engine().run();
    }
}
