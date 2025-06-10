package framework.graphics;

public class ShaderTextured extends Shader {

    public ShaderTextured() {
        super("resources/shaders/shader.vert", "resources/shaders/shader.frag");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {

    }
}
