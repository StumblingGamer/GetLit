package com.s1survival.getlit.torch;

import org.bukkit.Location;
import java.util.List;
import java.util.UUID;

public class PlacedTorchResult {
    private boolean _success;
    private int _numTorches;
    private UUID _playerUID;
    private String _playerName;
    private String _level;
    private int _topheight;
    private int _radius;
    private int _spacing;
    private List<Location> _locations;

    public PlacedTorchResult(boolean _success) {

        this._success = _success;
    }

    public PlacedTorchResult(boolean _success, int _numTorches, UUID _playerUID, String _playerName, String _level, int _topheight, int _radius, int _spacing, List<Location> _locations) {
       this._success = _success;
       this._numTorches = _numTorches;
       this._playerUID = _playerUID;
       this._playerName = _playerName;
       this._level = _level;
       this._topheight = _topheight;
       this._radius = _radius;
       this._spacing = _spacing;
       this._locations = _locations;
    }

    public boolean is_success() {

        return _success;
    }

    public void set_success(boolean _success) {

        this._success = _success;
    }
    public int get_numTorches() {

       return _numTorches;
    }
    public void set_numTorches(int _numTorches) {

       this._numTorches = _numTorches;
    }
    public UUID get_playerUID() {

       return _playerUID;
    }
    public void set_playerUID(UUID _playerUID) {

       this._playerUID = _playerUID;
    }
    public String get_playerName() {

       return _playerName;
    }
    public void set_playerName(String _playerName) {

       this._playerName = _playerName;
    }
    public String get_level() {

        return _level;
    }
    public int get_topheight() {

       return _topheight;
    }
    public void set_topheight(int _topheight) {

       this._topheight = _topheight;
    }
    public int get_radius() {

       return _radius;
    }
    public void set_radius(int _radius) {

       this._radius = _radius;
    }
    public int get_spacing() {

       return _spacing;
    }
    public void set_spacing(int _spacing) {

       this._spacing = _spacing;
    }
    public List<Location> get_locations() {

       return _locations;
    }
    public void set_locations(List<Location> _locations) {

        this._locations = _locations;
    }
}