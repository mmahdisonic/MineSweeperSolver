package ir.kharazmi.minesweepersolver;

import java.security.SecureRandom;

public class Operation {
    int width, height;
    int[][] table;
    boolean[][] click;
    boolean[][] flag;

    public Operation(int width, int height, int[][] table) {
        this.width = width;
        this.height = height;
        this.table = new int[width][height];
        this.click = new boolean[width][height];
        this.flag = new boolean[width][height];
        for (int i = 0; i < width; i++)
            System.arraycopy(table[i], 0, this.table[i], 0, height);
    }

    int getAdj(int x, int y, int val) {
        int ret = 0;
        if (x > 0 && y > 0 && table[x - 1][y - 1] == val)
            ret++;
        if (y > 0 && table[x][y - 1] == val)
            ret++;
        if (x > 0 && table[x - 1][y] == val)
            ret++;
        if (x < width - 1 && y > 0 && table[x + 1][y - 1] == val)
            ret++;
        if (x > 0 && y < height - 1 && table[x - 1][y + 1] == val)
            ret++;
        if (y < height - 1 && table[x][y + 1] == val)
            ret++;
        if (x < width - 1 && table[x + 1][y] == val)
            ret++;
        if (x < width - 1 && y < height - 1 && table[x + 1][y + 1] == val)
            ret++;
        return ret;
    }

    int getAdjClick(int x, int y) {
        int ret = 0;
        if (x > 0 && y > 0 && click[x - 1][y - 1])
            ret++;
        if (y > 0 && click[x][y - 1])
            ret++;
        if (x > 0 && click[x - 1][y])
            ret++;
        if (x < width - 1 && y > 0 && click[x + 1][y - 1])
            ret++;
        if (x > 0 && y < height - 1 && click[x - 1][y + 1])
            ret++;
        if (y < height - 1 && click[x][y + 1])
            ret++;
        if (x < width - 1 && click[x + 1][y])
            ret++;
        if (x < width - 1 && y < height - 1 && click[x + 1][y + 1])
            ret++;
        return ret;
    }

    void setAdj(int x, int y, int val, int setVal) {
        if (x > 0 && y > 0 && table[x - 1][y - 1] == val)
            table[x - 1][y - 1] = setVal;
        if (y > 0 && table[x][y - 1] == val)
            table[x][y - 1] = setVal;
        if (x > 0 && table[x - 1][y] == val)
            table[x - 1][y] = setVal;
        if (x < width - 1 && y > 0 && table[x + 1][y - 1] == val)
            table[x + 1][y - 1] = setVal;
        if (x > 0 && y < height - 1 && table[x - 1][y + 1] == val)
            table[x - 1][y + 1] = setVal;
        if (y < height - 1 && table[x][y + 1] == val)
            table[x][y + 1] = setVal;
        if (x < width - 1 && table[x + 1][y] == val)
            table[x + 1][y] = setVal;
        if (x < width - 1 && y < height - 1 && table[x + 1][y + 1] == val)
            table[x + 1][y + 1] = setVal;
    }

    void clickAdj(int x, int y) {
        if (x > 0 && y > 0 && table[x - 1][y - 1] == -1)
            click[x - 1][y - 1] = true;
        if (y > 0 && table[x][y - 1] == -1)
            click[x][y - 1] = true;
        if (x > 0 && table[x - 1][y] == -1)
            click[x - 1][y] = true;
        if (x < width - 1 && y > 0 && table[x + 1][y - 1] == -1)
            click[x + 1][y - 1] = true;
        if (x > 0 && y < height - 1 && table[x - 1][y + 1] == -1)
            click[x - 1][y + 1] = true;
        if (y < height - 1 && table[x][y + 1] == -1)
            click[x][y + 1] = true;
        if (x < width - 1 && table[x + 1][y] == -1)
            click[x + 1][y] = true;
        if (x < width - 1 && y < height - 1 && table[x + 1][y + 1] == -1)
            click[x + 1][y + 1] = true;
    }

    void flagAdj(int x, int y) {
        if (x > 0 && y > 0 && table[x - 1][y - 1] == -1)
            flag[x - 1][y - 1] = true;
        if (y > 0 && table[x][y - 1] == -1)
            flag[x][y - 1] = true;
        if (x > 0 && table[x - 1][y] == -1)
            flag[x - 1][y] = true;
        if (x < width - 1 && y > 0 && table[x + 1][y - 1] == -1)
            flag[x + 1][y - 1] = true;
        if (x > 0 && y < height - 1 && table[x - 1][y + 1] == -1)
            flag[x - 1][y + 1] = true;
        if (y < height - 1 && table[x][y + 1] == -1)
            flag[x][y + 1] = true;
        if (x < width - 1 && table[x + 1][y] == -1)
            flag[x + 1][y] = true;
        if (x < width - 1 && y < height - 1 && table[x + 1][y + 1] == -1)
            flag[x + 1][y + 1] = true;
    }

    void mineFinder() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (table[i][j] > 0 && getAdj(i, j, -1) > 0 && getAdj(i, j, -1) + getAdj(i, j, -2) == table[i][j]) {
                    flagAdj(i, j);
                    setAdj(i, j, -1, -2);
                }
    }

    void clickFinder() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if (table[i][j] > 0 && table[i][j] == getAdj(i, j, -2))
                    clickAdj(i, j);
            }
    }

    boolean checkContradiction() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (table[i][j] > 0 && getAdj(i, j, -2) > table[i][j])
                    return true;
                if (table[i][j] > 0 && getAdj(i, j, -1) - getAdjClick(i, j) < table[i][j] - getAdj(i, j, -2))
                    return true;
            }
        }
        return false;
    }

    int clickable() {
        int sum = 0;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (click[i][j])
                    sum++;
        return sum;
    }

    //TODO merge
    int remain() {
        int sum = 0;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (table[i][j] == -1)
                    sum++;
        return sum;
    }

    int flagable() {
        int sum = 0;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (flag[i][j])
                    sum++;
        return sum;
    }

    void update(ImageProcessor imageProcessor) {
        imageProcessor.updateBoard();
        if (imageProcessor.getBombCount() != 0) {
            imageProcessor.reset();
            update(imageProcessor);
        }
        int[][] t = imageProcessor.getTable();
        for (int i = 0; i < width; i++)
            System.arraycopy(t[i], 0, this.table[i], 0, height);
    }

    void randomClick(ImageProcessor imageProcessor) {
        int rand = new SecureRandom().nextInt(remain());
        int counter = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (table[i][j] == -1)
                    counter++;
                if (counter == rand) {
                    imageProcessor.click(i, j);
                    System.out.println("Random needed: " + i + " " + j);
                }
            }
        }
    }
}
