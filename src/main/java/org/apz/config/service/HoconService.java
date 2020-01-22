package org.apz.config.service;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apz.config.ConfigApp;
import org.apz.config.dto.ConfigDto;
import org.apz.config.dto.PropertyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.parser.ConfigDocument;
import com.typesafe.config.parser.ConfigDocumentFactory;

@Service
public class HoconService {

	private static final String PATH_ERROR_MESSAGE = "The parameter {} doesn't exist in the HOCON configuration";

	private static final Logger LOG = LoggerFactory.getLogger(HoconService.class);
	
	@Autowired
	private Config config;
	
	@Value("${resources.path}")
	private String resourcesPath;
	
	/**
	 * Metodo que devuelve un path que corresponde a una estructura compleja, hay
	 * que seguir navegando para llegar a los subniveles.
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Config> getSubtree(String path) {
		try {
			return (List<Config>) config.getConfigList(path);
		} catch (ConfigException e) {
			LOG.info(PATH_ERROR_MESSAGE, path);
			return null;
		}
	}
	
	/**
	 * Metodo que devuelve un path que corresponde a un boolean. Si no existe devuelve true
	 * 
	 * @param path
	 * @return
	 */
	public boolean getBoolean(String path) {
		try {
			return config.getBoolean(path);
		} catch (ConfigException e) {
			LOG.info(PATH_ERROR_MESSAGE, path);
			return true;
		}
	}
	
	/**
	 * Metodo que devuelve un path que corresponde a una cadena
	 * 
	 * @param path
	 * @return
	 */
	public String getString(String path) {
		try {
			return config.getString(path);
		} catch (ConfigException e) {
			LOG.info(PATH_ERROR_MESSAGE, path);
			return null;
		}
	}

	/**
	 * Metodo que devuelve un path que corresponde a una lista de cadenas
	 * 
	 * @param path
	 * @return
	 */
	public List<String> getStringList(String path) {
		try {
			return config.getStringList(path);
		} catch (ConfigException e) {
			LOG.info(PATH_ERROR_MESSAGE, path);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que comprueba si un path existe en el fichero de configuracion de
	 * subcatalogos
	 * 
	 * @param path
	 * @return
	 */
	public boolean hasPathOrNull(String path) {
		return config.hasPathOrNull(path);
	}

	/**
	 * Metodo para comprobar si una estructura esta vacia
	 * @param dataLevel
	 * @return
	 */
	public boolean isEmpty(Config dataLevel) {
		return null == dataLevel || dataLevel.isEmpty();
	}
	
	public void register(ConfigDto configDto) throws Exception {
		
		final File configSrcFile = new File(HoconService.class.getClassLoader().getResource("src/opac.conf").getFile());
		
		ConfigDocument configDocument = ConfigDocumentFactory.parseFile(configSrcFile);
		
		for (PropertyDto property : configDto.getProperties()) {
			configDocument = configDocument.withValueText(property.getKey(), property.getValue());
		}
		
		//overwrite src config file
		writeFile(configDocument.render(), configSrcFile.getPath());
		writeFile(configDocument.render(), resourcesPath + "src/opac.conf");
		
		//overwrite config file
		final URL urlConfigFile = ConfigApp.class.getClassLoader().getResource("opac.conf");
		final File configFile = new File(urlConfigFile.getFile());
		config = ConfigFactory.parseFile(configFile).resolve();
		
		
		final String content = renderFormat();
		writeFile(content, configFile.getPath());
		writeFile(content, resourcesPath + "opac.conf");
	}
	
	public String renderJson() {
		return config.root().render(ConfigRenderOptions.defaults().setJson(true).setComments(false).setOriginComments(false));
	}
	
	private String renderFormat() {
		return config.root().render(ConfigRenderOptions.defaults().setFormatted(true).setComments(false).setOriginComments(false));
	}
	
	private static void writeFile(String content, String path) throws Exception {
		LOG.info("Escritura de "+ path);
		Files.write(Paths.get(path), content.getBytes());
	}
	
	
	
	
}