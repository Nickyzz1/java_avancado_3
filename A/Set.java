import java.util.ArrayList;

public class Set<T>
{
    private ArrayList<SetItem<T>> Items;

    public Set()
    {
        this.Items = new ArrayList<>();
    }
    public Set(ArrayList<SetItem<T>> Items)
    {
        this.Items = new ArrayList<>(Items);
    }
    public Set(Set<T> OtherSet)
    {
        this.Items = new ArrayList<>(OtherSet.GetArrayList());
    }

    public int Size()
    {
        return Items.size();
    }

    public boolean AddItem(T Value)
    {
        return Items.add(new SetItem<>(Value));
    }
    public boolean AddItem(Set<T> Value)
    {
        return Items.add(new SetItem<>(Value));
    }
    public boolean AddItem(SetItem<T> Value)
    {
        return Items.add(new SetItem<>(Value));
    }

    public ArrayList<SetItem<T>> GetArrayList()
    {
        return Items;
    }

    public boolean Contains(T Value)
    {
        for(int i = 0; i < Items.size(); ++i)
        {
            if(Items.get(i).Compare(Value))
            {
                return true;
            }
        }
        return false;
    }

    public boolean Contains(Set<T> Value)
    {
        for(int i = 0; i < Items.size(); ++i)
        {
            if(Items.get(i).Compare(Value))
            {
                return true;
            }
        }
        return false;
    }

    public boolean Contains(SetItem<T> Value)
    {
        for(int i = 0; i < Items.size(); ++i)
        {
            if(Items.get(i).Compare(Value))
            {
                return true;
            }
        }
        return false;
    }

    public Set<T> Join(Set<T> OtherSet)
    {
        Set<T> NewSet = new Set<>(this);
        for(int i = 0; i < OtherSet.Items.size(); ++i)
        {
            if(!NewSet.Contains(OtherSet.Items.get(i)))
            {
                NewSet.AddItem(OtherSet.Items.get(i));
            }
        }
        return NewSet;
    }

    public Set<T> Intersect(Set<T> OtherSet)
    {
        Set<T> NewSet = new Set<>();
        for(int i = 0; i < OtherSet.Items.size(); ++i)
        {
            if(this.Contains(OtherSet.Items.get(i)))
            {
                NewSet.AddItem(OtherSet.Items.get(i));
            }
        }
        return NewSet;
    }

    public boolean Subset(Set<T> OtherSet)
    {
        Set<T> NewSet = new Set<>();
        for(int i = 0; i < OtherSet.Items.size(); ++i)
        {
            if(!this.Contains(OtherSet.Items.get(i)))
            {
                NewSet.AddItem(OtherSet.Items.get(i));
            }
        }
        return NewSet.Size() == 0;
    }

    public String toString()
    {
        return this.Items.toString();
    }
}