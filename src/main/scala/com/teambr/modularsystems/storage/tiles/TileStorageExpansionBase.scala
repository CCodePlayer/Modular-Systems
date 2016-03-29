package com.teambr.modularsystems.storage.tiles

/**
  * This file was created for Modular-Systems
  *
  * Modular-Systems is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis "pauljoda"
  * @since 3/28/2016
  */
class TileStorageExpansionBase extends TileStorageExpansion {

    /**
      * Called after this has been added to a network
      */
    override def addedToNetwork(): Unit = {}

    /**
      * Called right before this is removed from a network
      */
    override def removedFromNetwork(): Unit = {}
}
