public class ComplexMatrix {
    private ComplexNumber[][] data;
    private int rows;
    private int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = new ComplexNumber(0, 0);
            }
        }
    }

    public ComplexMatrix(ComplexNumber[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public ComplexNumber get(int i, int j) { return data[i][j]; }
    public void set(int i, int j, ComplexNumber val) { data[i][j] = val; }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("размеры матриц не совпадают для сложения");
        }
        ComplexNumber[][] res = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return new ComplexMatrix(res);
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("невозможно умножить");
        }
        ComplexNumber[][] res = new ComplexNumber[this.rows][other.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].mul(other.data[k][j]));
                }
                res[i][j] = sum;
            }
        }
        return new ComplexMatrix(res);
    }

    public ComplexMatrix transpose() {
        ComplexNumber[][] res = new ComplexNumber[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[j][i] = this.data[i][j];
            }
        }
        return new ComplexMatrix(res);
    }

    public ComplexNumber determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("определитель только для квадратных матриц");
        }
        if (rows == 1) return data[0][0];

        if (rows == 2) {

            ComplexNumber ad = data[0][0].mul(data[1][1]);
            ComplexNumber bc = data[0][1].mul(data[1][0]);
            return ad.sub(bc);
        }


        ComplexNumber det = new ComplexNumber(0, 0);
        for (int j = 0; j < cols; j++) {
            ComplexNumber[][] minorData = getMinor(0, j);
            ComplexMatrix minor = new ComplexMatrix(minorData);
            ComplexNumber cofactor = data[0][j].mul(minor.determinant());

            if (j % 2 == 0) det = det.add(cofactor);
            else det = det.sub(cofactor);
        }
        return det;
    }

    private ComplexNumber[][] getMinor(int row, int col) {
        int newSize = rows - 1;
        ComplexNumber[][] minor = new ComplexNumber[newSize][newSize];
        int rIndex = 0;
        for (int i = 0; i < rows; i++) {
            if (i == row) continue;
            int cIndex = 0;
            for (int j = 0; j < cols; j++) {
                if (j == col) continue;
                minor[rIndex][cIndex] = data[i][j];
                cIndex++;
            }
            rIndex++;
        }
        return minor;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        if (other.rows != other.cols) {
            throw new IllegalArgumentException("деление возможно только на квадратную матрицу");
        }
        ComplexMatrix invB = other.inverse();
        return this.multiply(invB);
    }

    private ComplexMatrix inverse() {
        ComplexNumber det = determinant();
        if (det.getRe() == 0 && det.getIm() == 0) {
            throw new ArithmeticException("матрица вырожденная, обратной нет");
        }

        if (rows == 1) {
            ComplexNumber[][] res = new ComplexNumber[1][1];
            res[0][0] = new ComplexNumber(1, 0).div(det);
            return new ComplexMatrix(res);
        }

        if (rows == 2) {
            ComplexNumber[][] res = new ComplexNumber[2][2];


            res[0][0] = data[1][1].div(det);
            res[0][1] = data[0][1].mul(new ComplexNumber(-1, 0)).div(det);
            res[1][0] = data[1][0].mul(new ComplexNumber(-1, 0)).div(det);
            res[1][1] = data[0][0].div(det);
            return new ComplexMatrix(res);
        }

        ComplexNumber[][] adj = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ComplexNumber[][] minorData = getMinor(i, j);
                ComplexNumber minorDet = new ComplexMatrix(minorData).determinant();
                ComplexNumber cofactor = minorDet;
                if ((i + j) % 2 != 0) {
                    cofactor = cofactor.mul(new ComplexNumber(-1, 0));
                }
                adj[j][i] = cofactor;
            }
        }

        ComplexMatrix adjMatrix = new ComplexMatrix(adj);
        ComplexNumber[][] res = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[i][j] = adjMatrix.get(i, j).div(det);
            }
        }
        return new ComplexMatrix(res);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%-15s", data[i][j].toString()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}