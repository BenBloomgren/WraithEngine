package net.whg.we.rendering.opengl;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.RawShaderCode;

public class GLShader implements IShader
{
    private final Map<String, Integer> uniforms = new HashMap<>();
    private final IOpenGL opengl;
    private final BindStates bindStates;
    private int shaderId;
    private boolean disposed;
    private boolean created;

    /**
     * Creates a new OpenGL shader object.
     * 
     * @param bindStates
     *     - The container for binding GPU states.
     * @param opengl
     *     - The OpenGL instance.
     */
    GLShader(BindStates bindStates, IOpenGL opengl)
    {
        this.bindStates = bindStates;
        this.opengl = opengl;
    }

    @Override
    public void bind()
    {
        bindStates.bindShader(shaderId);
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        destroyShader();
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    /**
     * Destroys the current shader object from the GPU.
     */
    private void destroyShader()
    {
        if (bindStates.getBoundShader() == shaderId)
            bindStates.bindShader(0);

        opengl.deleteShaderProgram(shaderId);
    }

    @Override
    public void compile(RawShaderCode shaderCode)
    {
        if (shaderCode == null)
            throw new IllegalArgumentException("Shader code cannot be null!");

        if (created)
            destroyShader();

        created = true;

        int vId = opengl.createVertexShader(shaderCode.getVertexShader());
        int fId = opengl.createFragementShader(shaderCode.getFragmentShader());
        shaderId = opengl.createShaderProgram();

        opengl.attachShaderProgram(shaderId, vId);
        opengl.attachShaderProgram(shaderId, fId);
        opengl.linkShader(shaderId);

        opengl.deleteShader(vId);
        opengl.deleteShader(fId);
    }

    private int getUniform(String uniform)
    {
        if (uniforms.containsKey(uniform))
            return uniforms.get(uniform);

        int loc = opengl.getUniformLocation(shaderId, uniform);
        uniforms.put(uniform, loc);
        return loc;
    }

    @Override
    public void setUniformMat4(String property, FloatBuffer value)
    {
        bind();

        int loc = getUniform(property);
        opengl.setUniformMat4(loc, value);
    }
}
