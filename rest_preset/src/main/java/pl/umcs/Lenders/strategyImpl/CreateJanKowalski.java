package pl.umcs.Lenders.strategyImpl;

import pl.umcs.basis.lenderinfo.Lender;

public class CreateJanKowalski implements LenderRecordCreator{
    @Override
    public Lender createLender() {
        return new Lender(1l, "Jan", "Kowalski");
    }
}
