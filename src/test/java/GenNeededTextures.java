public class GenNeededTextures {
    public static void main(String[] args) {
        String[] toolTypes = {
                "shortsword",
                "broadsword",
                "longsword",
                "greatsword",
                "rapier",
                "shortspear",
                "longspear",
                "javelin",
                "mace",
                "morningstar",
                "greataxe",
                "greathammer",
                "warhammer",
                "axe",
                "pickaxe",
                "shovel",
                "hoe",
                "hammer"
        };
        System.out.println("The following should be a complete tool with a distinct head, binding, and handle. Make the part of the texture that will be a part of the head red, the part of the texture that will be the binding green, and the part that will be the handle blue. Make sure to use only a single color channel for each part. See the pickaxe example.");
        for(String type : toolTypes) {
            System.out.println(type);
        }
        String[] parts = {
                "handle",
                "long handle",
                "short shaft",
                "long shaft",
                "very long shaft",
                "javelin shaft",
                "binding",
                "tough binding",
                "slim hilt",
                "hilt",
                "wide hilt",
                "shortsword blade",
                "broadsword blade",
                "longsword blade",
                "greatsword blade",
                "rapier blade",
                "spearhead",
                "macehead",
                "morningstar head",
                "greataxe head",
                "greathammer head",
                "warhammer head",
                "axe head",
                "pickaxe head",
                "shovel head",
                "hoe head",
                "hammer head"
        };
        System.out.println("\nThe following parts should be standalone, as if items in an inventory. Many of the heads can probably just be edited from the tools. Bindings could be either inspired by Tinker's Construct (as in, a small X shape), or custom designs. Handles and shafts should be roughly stick-like. These should all be grayscale.");
        for(String part : parts) {
            System.out.println(part);
        }
    }
}
