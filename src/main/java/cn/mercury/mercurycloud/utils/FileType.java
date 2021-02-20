package cn.mercury.mercurycloud.utils;

/**
 * @Author: Mercury-Z
 */
public enum FileType{
    document(1),
    image(2),
    video(3),
    audio(4),
    other(5);

    private final int id;
    FileType(int id) {
        this.id = id;
    }
    public int getValue() {
        return id;
    }
}