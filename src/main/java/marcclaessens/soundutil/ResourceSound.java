package marcclaessens.soundutil;

import java.io.InputStream;

public class ResourceSound implements Sound { 
	private final String resource;
	private final SoundFormat type;
	
	public ResourceSound(String resource) {
		this.resource = resource;
		String ext = resource.substring(resource.lastIndexOf('.') +1);
		type = SoundFormat.fromExtension(ext);
	}

	@Override
	public InputStream getSoundStream() {
		return getClass().getResourceAsStream(resource);
	}

	@Override
	public SoundFormat getType() {
		return type;
	}
}
