/**
 * 
 */
package com.gigety.web.api.conf;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import com.gigety.web.api.exception.GigetyException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author samuelsegal This runs on docker container and retrieves all docker
 *         secrets which are set inour docker-compose
 *
 */
@Slf4j
@Profile({ "prod" })
public class DockerSecretsProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

		try {

			System.out.println("DOCKER SECRETS ----------()()--------------");

			String dockerSecretsBindPath = environment.getProperty("docker-secret.bind-path");
			if (dockerSecretsBindPath == null || dockerSecretsBindPath.isEmpty()) {
				System.out.println("------------------ NO SECRETS........");
				System.out.println(
						"No Secrets Configured for this environment: Configuration parameters will be set in machine env variables."
								+ dockerSecretsBindPath);
				return;
			}

			System.out.println("docker-secret.bind-path: " + dockerSecretsBindPath);

			Path bindPath = Paths.get(dockerSecretsBindPath);
			// Properties props = new Properties();
			Map<String, Object> dockerSecrets = new HashMap<>();
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(bindPath)) {
				System.out.println("got stream:");
				for (Path entry : stream) {

					byte[] secretBytes = Files.readAllBytes(entry);
					String secret = new String(secretBytes, StandardCharsets.UTF_8);
					// TODO:: Remove this debug even though its just debug
					System.out.println(secret);
					System.out.println("Docker Secret :: " + entry.getFileName().toString() + " :: " + secret);
					dockerSecrets.put(entry.getFileName().toString(), secret);
				}
			}
			MapPropertySource properties = new MapPropertySource("docker-secrets", dockerSecrets);
			environment.getPropertySources().addLast(properties);

		} catch (Exception e) {
			String msg = "An error occurred reading docker secrets";
			System.out.println(msg);
			//log.error("{} :: {}", msg, e);
			throw new GigetyException(e.getMessage());
		}
	}
}
