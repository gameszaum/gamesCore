package com.gameszaum.core.spigot.api.location;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Represents an immutable cubic-like area in a world
 */
@SerializableAs("Cuboid")
public final class Cuboid implements ConfigurationSerializable, Iterable<Block> {

    private final String worldName;
    private final Vector minimumPoint;
    private final Vector maximumPoint;

    /**
     * Constructs a cuboid of exactly 1 block
     *
     * @param loc the location of the block
     */
    public Cuboid(Location loc) {
        this(loc, loc);
    }

    /**
     * Constructs a cuboid ranging from the minimum components of both
     * locations, to the maximum. The locations must the in the same
     * <code>World</code>
     *
     * @param locOne the first location
     * @param locTwo The second location
     * @throws NullPointerException     if <code>locOne</code>, <code>locOne</code>, or their
     *                                  respective <code>World</code>s are <code>null</code>
     * @throws IllegalArgumentException if <code>locOne</code> and <code>locOne</code> are not in the
     *                                  same <code>World</code>
     */
    public Cuboid(Location locOne, Location locTwo) {
        Preconditions.checkNotNull(locOne);
        Preconditions.checkNotNull(locTwo);
        Preconditions.checkNotNull(locOne.getWorld());
        Preconditions.checkNotNull(locTwo.getWorld());

        this.worldName = locOne.getWorld().getName().toLowerCase();
        this.minimumPoint = locOne.toVector();
        this.maximumPoint = locTwo.toVector();

        Preconditions.checkArgument(locOne.getWorld().equals(locTwo.getWorld()), "locations must be in the same world");
    }

    /**
     * Constructs a cuboid ranging from the minimum components of both vectors,
     * to the maximum
     *
     * @param worldName the name of the <code>World</code>
     * @param vecOne    the first vector
     * @param vecTwo    the second vector
     * @throws NullPointerException if <code>worldName</code>, <code>vecOne</code> or
     *                              <code>vecTwo</code> are <code>null</code>
     */
    public Cuboid(String worldName, Vector vecOne, Vector vecTwo) {
        Preconditions.checkNotNull(worldName);
        Preconditions.checkNotNull(vecOne);
        Preconditions.checkNotNull(vecTwo);

        this.worldName = worldName;
        this.minimumPoint = Vector.getMinimum(vecOne, vecTwo);
        this.maximumPoint = Vector.getMaximum(vecOne, vecTwo);
    }

    /**
     * Constructs a new <code>Cuboid</code> using the existing coordinates and
     * the specified world name
     *
     * @param worldName the name of the <code>World</code>
     * @return the new <code>Cuboid</code>
     * @throws NullPointerException if <code>worldName</code> is <code>null</code>
     */
    public Cuboid withWorld(String worldName) {
        return new Cuboid(worldName, minimumPoint, maximumPoint);
    }

    /**
     * Constructs a new <code>Cuboid</code> using the existing world name and
     * the specified coordinates
     *
     * @param vecOne the first vector
     * @param vecTwo the second vector
     * @return the new <code>Cuboid</code>
     * @throws NullPointerException if <code>vecOne</code> or <code>vecTwo</code> are
     *                              <code>null</code>
     */
    public Cuboid withCoordinates(Vector vecOne, Vector vecTwo) {
        return new Cuboid(worldName, vecOne, vecTwo);
    }

    /**
     * Checks if the <code>Cuboid</code> contains the specified location. If the
     * location is not in the same <code>World</code>, it will return
     * <code>false</code>
     *
     * @param loc the location
     * @return if the <code>Cuboid</code> contains it
     * @throws NullPointerException if <code>loc</code> or its respective <code>World</code> are
     *                              <code>null</code>
     */
    public boolean contains(Location loc) {
        Preconditions.checkNotNull(loc);
        Preconditions.checkNotNull(loc.getWorld());

        return loc.getWorld().getName().equalsIgnoreCase(worldName)
                && loc.toVector().isInAABB(minimumPoint, maximumPoint);
    }

    /**
     * Checks if the <code>Cuboid</code> contains the specified vector
     *
     * @param vec the vector
     * @return if the <code>Cuboid</code> contains it
     * @throws NullPointerException if <code>vec</code> is <code>null</code>
     */
    public boolean contains(Vector vec) {
        Preconditions.checkNotNull(vec);

        return vec.isInAABB(minimumPoint, maximumPoint);
    }

    /**
     * The <code>World</code>
     *
     * @return the world, or <code>null</code> if it is unloaded or doesn't
     * exist
     */
    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    /**
     * A list of blocks
     *
     * @return the list of blocks
     * @throws IllegalStateException if the <code>World</code> is either unloaded or doesn't exist
     */
    public List<Block> getBlocks() {
        World world = getWorld();
        Preconditions.checkState(world != null, "the world is either unloaded or doesn't exist");

        List<Block> blocks = new ArrayList<>();
        for (int x = minimumPoint.getBlockX(); x <= maximumPoint.getBlockX(); x++) {
            for (int y = minimumPoint.getBlockY(); y <= maximumPoint.getBlockY(); y++) {
                for (int z = minimumPoint.getBlockZ(); z <= maximumPoint.getBlockZ(); z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    /**
     * The lower-most <code>Location</code>
     *
     * @return the lower-most location
     * @throws IllegalStateException if the <code>World</code> is either unloaded or doesn't exist
     */
    public Location getLowerLocation() {
        World world = getWorld();
        Preconditions.checkState(world != null, "the world is either unloaded or doesn't exist");

        return new Location(world, getLowerX(), getLowerY(), getLowerZ());
    }

    /**
     * The upper-most <code>Location</code>
     *
     * @return the upper-most location
     * @throws IllegalStateException if the <code>World</code> is either unloaded or doesn't exist
     */
    public Location getUpperLocation() {
        World world = getWorld();
        Preconditions.checkState(world != null, "the world is either unloaded or doesn't exist");

        return new Location(world, getUpperX(), getUpperY(), getUpperZ());
    }

    /**
     * The lower-most <code>Vector</code>
     *
     * @return the lower-most vector
     */
    public Vector getLowerVector() {
        return new Vector(getLowerX(), getLowerY(), getLowerZ());
    }

    /**
     * The upper-most <code>Vector</code>
     *
     * @return the upper-most vector
     */
    public Vector getUpperVector() {
        return new Vector(getUpperX(), getUpperY(), getUpperZ());
    }

    /**
     * The lower-most X coordinate
     *
     * @return the lower-most X
     */
    public int getLowerX() {
        return minimumPoint.getBlockX();
    }

    /**
     * The lower-most Y coordinate
     *
     * @return the lower-most Y
     */
    public int getLowerY() {
        return minimumPoint.getBlockY();
    }

    /**
     * The lower-most Z coordinate
     *
     * @return the lower-most Z
     */
    public int getLowerZ() {
        return minimumPoint.getBlockZ();
    }

    /**
     * The upper-most X coordinate
     *
     * @return the upper-most X
     */
    public int getUpperX() {
        return maximumPoint.getBlockX();
    }

    /**
     * The upper-most Y coordinate
     *
     * @return the upper-most Y
     */
    public int getUpperY() {
        return maximumPoint.getBlockY();
    }

    /**
     * The upper-most Z coordinate
     *
     * @return the upper-most Z
     */
    public int getUpperZ() {
        return maximumPoint.getBlockZ();
    }

    /**
     * The volume in blocks
     *
     * @return the volume
     */
    public long getVolume() {
        return (getUpperX() - getLowerX() + 1) * (getUpperY() - getLowerY() + 1) * (getUpperZ() - getLowerZ() + 1);
    }

    /**
     * An iterator ranging from the lower-most block of the <code>Cuboid</code>,
     * to the upper-most
     *
     * @return An iterator of all blocks
     * @throws IllegalStateException if the <code>World</code> is either unloaded or doesn't exist
     */
    @Override
    public Iterator<Block> iterator() {
        World world = getWorld();
        Preconditions.checkState(world != null, "the world is either unloaded or doesn't exist");

        return getBlocks().iterator();
    }

    /**
     * Serializes the <code>Cuboid</code> into a map
     *
     * @return the map containing the serialized form
     */
    @Override
    public Map<String, Object> serialize() {
        // LinkedHashMap to keep insertion order
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("world-name", worldName);
        map.put("x1", getLowerX());
        map.put("x2", getUpperX());
        map.put("y1", getLowerY());
        map.put("y2", getUpperY());
        map.put("z1", getLowerZ());
        map.put("z2", getUpperZ());

        return map;
    }

    /**
     * Deserializes the <code>Cuboid</code> contained on the given map
     *
     * @param map the map containing the serialized form
     * @return the deserialized <code>Cuboid</code>
     * @throws IllegalArgumentException if <code>map</code> is either missing one of the components
     *                                  of the serialized form, or the component is of the wrong type
     */
    public static Cuboid deserialize(Map<String, Object> map) {
        String worldName = (String) map.get("world-name");
        int xOne = (int) map.get("x1");
        int xTwo = (int) map.get("x2");
        int yOne = (int) map.get("y1");
        int yTwo = (int) map.get("y2");
        int zOne = (int) map.get("z1");
        int zTwo = (int) map.get("z2");

        Vector vecOne = new Vector(xOne, yOne, zOne);
        Vector vecTwo = new Vector(xTwo, yTwo, zTwo);

        return new Cuboid(worldName, vecOne, vecTwo);
    }
}