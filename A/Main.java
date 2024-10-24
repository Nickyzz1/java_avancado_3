public class Main
{
    public static void main(String[] args)
    {
        Set<Integer> Set1 = new Set<>();
        Set<Integer> Set2 = new Set<>();
        Set<Integer> Set3 = new Set<>();

        Set1.AddItem(1);
        Set1.AddItem(2);
        Set1.AddItem(3);

        Set2.AddItem(2);
        Set2.AddItem(3);
        Set2.AddItem(4);

        Set3.AddItem(1);
        Set3.AddItem(2);

        Set2.AddItem(Set3);

        System.out.println(Set1.Contains(2));
        System.out.println(Set1.Contains(4));
        System.out.println(Set2.Contains(Set3));
        System.out.println(Set2.Contains(1));

        System.out.println(Set1.Join(Set2).toString());
        System.out.println(Set1.Intersect(Set2).toString());

        System.out.println(Set1.Subset(Set3));
        System.out.println(Set1.Subset(Set2));
    }
}