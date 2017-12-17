package com.jayden.officialandroiddemo.multimedia.simplephotoandvideo;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);
}
