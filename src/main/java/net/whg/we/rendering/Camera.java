package net.whg.we.rendering;

import org.joml.Matrix4f;
import net.whg.we.util.Transform3D;

/**
 * The camera is the object in charge of determing the projection and view
 * matrices for how a scene should be rendered.
 */
public class Camera
{
    private final Transform3D transform = new Transform3D();
    private final Matrix4f projectionMatrix = new Matrix4f();
    private float fov = (float) Math.toRadians(90f);
    private float nearClip = 0.1f;
    private float farClip = 1000f;

    /**
     * Creates a new camera object with the default projection matrix.
     */
    public Camera()
    {
        rebuildProjectionMatrix();
    }

    /**
     * Updates the current projection matrix to match the current settings.
     */
    private void rebuildProjectionMatrix()
    {
        // TODO Get actual screen size.

        float aspect = 4f / 3f;

        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspect, nearClip, farClip);
    }

    /**
     * Gets the current projection matrix generated by this camera.
     * 
     * @return The projection matrix.
     */
    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }

    /**
     * Gets the current field of view for this camera.
     * 
     * @return The field of view.
     */
    public float getFov()
    {
        return fov;
    }

    /**
     * Assigns a new field of view for this camera.
     * 
     * @param fov
     *     - The new field of view for this camera, in radians.
     */
    public void setFov(float fov)
    {
        this.fov = fov;
        rebuildProjectionMatrix();
    }

    /**
     * Gets the near clipping plane distance.
     * 
     * @return The near clipping plane.
     */
    public float getNearClip()
    {
        return nearClip;
    }

    /**
     * Gets the far clipping place distance.
     * 
     * @return The far clipping plane.
     */
    public float getFarClip()
    {
        return farClip;
    }

    /**
     * Assigns the distances for the clipping planes.
     * 
     * @param near
     *     - The distance to the near clipping plane.
     * @param far
     *     - The distance to the far clipping plane.
     */
    public void setClippingDistance(float near, float far)
    {
        nearClip = near;
        farClip = far;

        rebuildProjectionMatrix();
    }

    /**
     * Gets the transformation object for this camera.
     * 
     * @return The transformation object.
     */
    public Transform3D getTransform()
    {
        return transform;
    }
}
