package graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class Shader {

    private int programID;
    private int vertID;
    private int fragID;

    private FloatBuffer matrix = BufferUtils.createFloatBuffer(16);

    public Shader(String vertPath, String fragPath) {
        vertID = loadShader(vertPath, GL20.GL_VERTEX_SHADER);
        fragID = loadShader(fragPath, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertID);
        GL20.glAttachShader(programID, fragID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    public void use() {
        GL20.glUseProgram(programID);
    }

    public void disable() {
        GL20.glUseProgram(0);
    }

    private static int loadShader(String path, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null)
                shaderSource.append(line).append("\n");
            reader.close();
        } catch (IOException e){
            System.err.println("Unable to read file with path " + path);
            e.printStackTrace();
            System.exit(-1);
        }

        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, shaderSource);
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 512));
            System.err.println("Unable to compile the shader");
            System.exit(-1);
        }
        return shader;
    }

    protected int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(programID, name);
    }

    protected void bindAttribute(int attrib, String name) {
        GL20.glBindAttribLocation(programID, attrib, name);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(int location, boolean value) {
        float tovec = 0;
        if(value) {
            tovec = 1;
        }
        GL20.glUniform1f(location, tovec);
    }

    protected void loadMatrix(int location, Matrix4f value) {
        value.get(matrix);
        GL20.glUniformMatrix4fv(location, false, matrix);
    }

    protected abstract void bindAttributes();

    protected abstract void getAllUniformLocations();

}
