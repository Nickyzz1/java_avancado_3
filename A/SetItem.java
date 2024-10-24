public class SetItem<T>
{
    private T Value;
    private Set<T> SValue;

    public SetItem()
    {
        this.Value = null;
        this.SValue = null;
    }
    public SetItem(T Value)
    {
        this.Value = Value;
        this.SValue = null;
    }
    public SetItem(Set<T> Value)
    {
        this.Value = null;
        this.SValue = Value;
    }
    public SetItem(SetItem<T> Value)
    {
        this.Value = Value.Value;
        this.SValue = Value.SValue;
    }

    public void SetValue(T Value)
    {
        this.Value = Value;
        this.SValue = null;
    }
    public void SetValue(Set<T> Value)
    {
        this.Value = null;
        this.SValue = Value;
    }
    public Object GetValue()
    {
        if(SValue == null)
        {
            return Value;
        }
        return SValue;
    }

    public boolean Compare(SetItem<T> Item)
    {
        if(this.Value == Item.Value && this.SValue == Item.SValue)
        {
            return true;
        }
        return false;
    }

    public boolean Compare(T Value)
    {
        if(this.SValue == null)
        {
            if(this.Value == Value)
            {
                return true;
            }
            return false;
        }else
        {
            return SValue.Contains(Value);
        }
    }

    public boolean Compare(Set<T> Value)
    {
        if(this.Value == null)
        {
            if(this.SValue == Value)
            {
                return true;
            }
            return SValue.Contains(Value);
        }else
        {
            return false;
        }
    }

    public String toString()
    {
        if(Value != null)
        {
            return Value.toString();
        }
        if(SValue != null)
        {
            return SValue.toString();
        }
        return "null";
    }
}