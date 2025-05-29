#version 330 core

in vec3 FragPos;
in vec3 Normal;

out vec4 outputColor;

uniform vec3 viewPos;
uniform vec3 lightColor = vec3(1.0, 1.0, 1.0);
uniform vec3 objectColor = vec3(1.0, 0.0, 1.0);

void main()
{
    float intensity = 1/distance(FragPos, viewPos);

    // Ambient lighting
    float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * lightColor;

    // Diffuse lighting (Lambertian reflectance)
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(viewPos - FragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor * intensity;

    // Specular lighting (Phong model)
    float specularStrength = 0.5;
    vec3 viewDir = normalize(viewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * lightColor * intensity;

    // Combine all lighting components
    vec3 result = (ambient + diffuse + specular * 0.01) * objectColor;
    outputColor = vec4(result, 1.0);
}