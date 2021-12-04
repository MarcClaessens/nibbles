package marcclaessens.soundutil;

import java.io.InputStream;

public interface Sound {
	InputStream getSoundStream();
	SoundFormat getType();
}
