package pl.umcs.Lenders.strategyImpl;

import pl.umcs.basis.lenderinfo.Lender;

public class CreateEmiliaNowak implements LenderRecordCreator{
    @Override
    public Lender createLender() {
        return new Lender(2l, "Emilia", "Nowak");
    }
}
