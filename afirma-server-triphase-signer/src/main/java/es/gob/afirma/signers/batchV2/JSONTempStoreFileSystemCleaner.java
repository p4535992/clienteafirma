/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * You may contact the copyright holder at: soporte.afirma@seap.minhap.es
 */

package es.gob.afirma.signers.batchV2;

import java.io.File;
import java.util.Date;

import es.gob.afirma.signers.batch.BatchConfigManager;

/**
 * Clase para la limpieza del directorio de temporales de las operaciones de lotes.
 * Estable un periodo de caducidad y todos los ficheros del directorio que existan
 * desde antes de ese periodo se eliminar&aacute;n.
 */
public class JSONTempStoreFileSystemCleaner implements Runnable {

	/** Tiempo de caducidad de los temporales. */
	private static final int EXPIRED_PERIOD = 300000; // 5 minutos

	private static boolean runningCleaning = false;

	/**
	 * Indica si el proceso se est&aacute; ejecutando actualmente.
	 * @return {@code true} si el proceso de limpieza se est&aacute; ejecutando,
	 * {@code false} si no.
	 */
	static boolean isRunningCleaning() {
		return runningCleaning;
	}

	@Override
	public void run() {

		runningCleaning = true;

		final long limitTime = new Date().getTime() - EXPIRED_PERIOD;
		for (final File file : BatchConfigManager.getTempDir().listFiles()) {
			if (file.isFile() && file.lastModified() < limitTime) {
				file.delete();
			}
		}
		runningCleaning = false;
	}
}
