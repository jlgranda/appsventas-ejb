delete from factura_electronica;
delete from membership;
delete from subject where confirmed = false;
delete from bussinesentity  where codetype = 'NUMERO_FACTURA';
delete from bussinesentity  where codetype = 'CEDULA';
