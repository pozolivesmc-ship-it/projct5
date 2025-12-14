/**
 * This is the InternalNode class
 * @author {Giovanni Garcia}
 * @version {12.13.2025}
 */
public class InternalNode implements BinNode {
    private BinNode left;
    private BinNode right;

    private static int addUnique(AirObject obj, AirObject[] arr, int count) {
        for (int i = 0; i < count; i++) {
            if (arr[i] == obj) {
                return count;
            }
        }
        if (count < arr.length) {
            arr[count] = obj;
            return count + 1;
        }
        return count;
    }


    private static int gatherObjects(
        BinNode node,
        AirObject[] buffer,
        int count) {
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode)node;
            for (int i = 0; i < leaf.getSize(); i++) {
                count = addUnique(leaf.getObject(i), buffer, count);
            }
            return count;
        }
        if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode)node;
            count = gatherObjects(internal.left, buffer, count);
            count = gatherObjects(internal.right, buffer, count);
        }
        return count;
    }


    public InternalNode() {
        this.left = Bintree.FLYWEIGHT;
        this.right = Bintree.FLYWEIGHT;
    }


    public void print(
        StringBuilder sb,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        Bintree.spacing(sb, depth);
        sb.append("I (");
        sb.append(x).append(", ").append(y).append(", ");
        sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
        sb.append(d).append(") ").append(depth).append("\r\n");

        int split = depth % 3;
        // X split then y split then z split
        if (split == 0) {
            int half = w / 2;
            this.left.print(sb, x, y, z, half, h, d, depth + 1);
            this.right.print(sb, x + half, y, z, w - half, h, d, depth + 1);
        }
        else if (split == 1) {
            int half = h / 2;
            this.left.print(sb, x, y, z, w, half, d, depth + 1);
            this.right.print(sb, x, y + half, z, w, h - half, d, depth + 1);
        }
        else {
            int half = d / 2;
            this.left.print(sb, x, y, z, w, h, half, depth + 1);
            this.right.print(sb, x, y, z + half, w, h, d - half, depth + 1);
        }
    }


    public int countNodes() {
        return 1 + this.left.countNodes() + this.right.countNodes();
    }


    public BinNode insert(
        AirObject obj,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        int split = depth % 3;
        if (split == 0) {
            int half = w / 2;
            int rightWidth = w - half;
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, half,
                h, d)) {
                this.left = this.left.insert(obj, x, y, z, half, h, d, depth
                    + 1);
            }
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x + half, y, z,
                rightWidth, h, d)) {
                this.right = this.right.insert(obj, x + half, y, z, rightWidth, 
                    h, d, depth + 1);
            }
        }
        else if (split == 1) {
            int half = h / 2;
            int rightHeight = h - half;
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w,
                half, d)) {
                this.left = this.left.insert(obj, x, y, z, w, half, d, depth
                    + 1);
            }
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y + half, z,
                w, rightHeight, d)) {
                this.right = this.right.insert(obj, x, y + half, z, w,
                    rightHeight, d, depth + 1);
            }
        }
        else {
            int half = d / 2;
            int rightDepth = d - half;
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, h,
                half)) {
                this.left = this.left.insert(obj, x, y, z, w, h, half, depth
                    + 1);
            }
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z + half,
                w, h, rightDepth)) {
                this.right = this.right.insert(obj, x, y, z + half, w, h,
                    rightDepth, depth + 1);
            }
        }
        return this;
    }


    public BinNode remove(
        AirObject obj,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        int split = depth % 3;
        if (split == 0) {
            int half = w / 2;
            int rightWidth = w - half;
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, half,
                h, d)) {
                this.left = this.left.remove(obj, x, y, z, half, h, d, depth
                    + 1);
            }
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x + half, y, z,
                rightWidth, h, d)) {
                this.right = this.right.remove(obj, x + half, y, z, rightWidth,
                    h, d, depth + 1);
            }
        }
        else if (split == 1) {
            int half = h / 2;
            int rightHeight = h - half;
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w,
                half, d)) {
                this.left = this.left.remove(obj, x, y, z, w, half, d, depth
                    + 1);
            }
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y + half, z,
                w, rightHeight, d)) {
                this.right = this.right.remove(obj, x, y + half, z, w,
                    rightHeight, d, depth + 1);
            }
        }
        else {
            int half = d / 2;
            int rightDepth = d - half;
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, h,
                half)) {
                this.left = this.left.remove(obj, x, y, z, w, h, half, depth
                    + 1);
            }
            if (Bintree.overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(), obj
                .getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z + half,
                w, h, rightDepth)) {
                this.right = this.right.remove(obj, x, y, z + half, w, h,
                    rightDepth, depth + 1);
            }
        }
        if (this.left == Bintree.FLYWEIGHT && this.right == Bintree.FLYWEIGHT) {
            return Bintree.FLYWEIGHT;
        }
        if (this.left instanceof LeafNode && this.right instanceof LeafNode) {
            LeafNode l = (LeafNode)this.left;
            LeafNode r = (LeafNode)this.right;

            LeafNode combined = new LeafNode();
            for (int i = 0; i < l.getSize(); i++) {
                combined.addObject(l.getObject(i));
            }
            for (int i = 0; i < r.getSize(); i++) {
                combined.addObject(r.getObject(i));
            }
            if (combined.getSize() <= Bintree.LEAF_MAX || combined
                .hasCommonIntersection()) {
                return combined;
            }
        }
        AirObject[] buffer = new AirObject[32];
        int count = gatherObjects(this, buffer, 0);
        if (count <= Bintree.LEAF_MAX) {
            LeafNode merged = new LeafNode();
            for (int i = 0; i < count; i++) {
                merged.addObject(buffer[i]);
            }
            return merged;
        }
        return this;
    }


    public void intersect(
        StringBuilder sb,
        int x1,
        int y1,
        int z1,
        int w1,
        int h1,
        int d1,
        int x2,
        int y2,
        int z2,
        int w2,
        int h2,
        int d2,
        int depth,
        int[] visited) {
        if (!Bintree.overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2)) {
            return;
        }
        visited[0]++;
        Bintree.spacing(sb, depth);
        sb.append("In Internal node (").append(x2).append(", ").append(y2)
            .append(", ");
        sb.append(z2).append(", ").append(w2).append(", ").append(h2).append(
            ", ");
        sb.append(d2).append(") ").append(depth).append("\r\n");

        int split = depth % 3;
        if (split == 0) {
            int half = w2 / 2;
            this.left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2, half,
                h2, d2, depth + 1, visited);
            this.right.intersect(sb, x1, y1, z1, w1, h1, d1, x2 + half, y2, z2,
                w2 - half, h2, d2, depth + 1, visited);
        }
        else if (split == 1) {
            int half = h2 / 2;
            this.left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2, w2,
                half, d2, depth + 1, visited);
            this.right.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2 + half, z2,
                w2, h2 - half, d2, depth + 1, visited);
        }
        else {
            int half = d2 / 2;
            this.left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2,
                half, depth + 1, visited);
            this.right.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2 + half,
                w2, h2, d2 - half, depth + 1, visited);
        }
    }
    public void collisions(
        StringBuilder sb,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        int split = depth % 3;
        // X split then y split then z split
        if (split == 0) {
            int half = w / 2;
            this.left.collisions(sb, x, y, z, half, h, d, depth + 1);
            this.right.collisions(sb, x + half, y, z, w - half, h, d, depth
                + 1);
        }
        else if (split == 1) {
            int half = h / 2;
            this.left.collisions(sb, x, y, z, w, half, d, depth + 1);
            this.right.collisions(sb, x, y + half, z, w, h - half, d, depth
                + 1);
        }
        else {
            int half = d / 2;
            this.left.collisions(sb, x, y, z, w, h, half, depth + 1);
            this.right.collisions(sb, x, y, z + half, w, h, d - half, depth
                + 1);
        }
    }
}