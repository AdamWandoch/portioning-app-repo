# Fillet portioning algorithm
### For greatly improved yield

During work at a fish processing plant on the cod portioning process using one of Marel's I-Cut portioners, I discovered the cutting pattern could be based on the total fillet size to utilize the material better.

[PROCESSING MACHINE SITE](https://marel.com/en/products/i-cut-11/fish/salmon)

The finished product is a vacuum-packed tray with two cod pieces that add up to 250g in this case. Ideally, we would only want to use 125g pieces, this however would create an awful amount of waste. To comply with industry standards it has been approved to tolerate smaller and bigger pieces: in one pack 110g minimum and 140g max. Fillets come in two grades: 225g to 450g or 450g to 900g.

This way when the fillet was ie.: 612g it could be cut into 125g + 125g + 125g + 125g + 112g and from the next fillet we would need to first cut a "priority cut" of 128g (250g target minus 112g sized offcut). This was controlled by the operator: when I saw that a lot of 110g to 115g pieces were coming of I had to manually change the portioner setting to target for bigger cuts to compliment the small ones until the balance was more or less right then switch back to 125g target. This could be definitely controlled automatically by the I-Cut software.

Furthermore, what if the off-cut size was smaller than the minimum tolerated 110g piece? It would go to the waste tray and would be hand chopped later for seafood chowder (a product of a far lesser value).

Then I thought that if the offcut wasn't big enough to be used on its own, but small enough to be "added" to the last target cut without exceeding the maximum tolerated 140g we would have far less waste. Ie. for a fillet of 260g that we cut 125+125+10, we would do 125+135 instead and then from the next fillet cut a priority cut of 115g (250total minus 135).

This got rid of small offcuts, smaller than 15g (140max tolerated size minus 125 standard cut). Unfortunately, I was left with offcuts of a size between 15g and 110g: quite a lot. So I thought I could "grow" more than the last target cut. I could take 15g from the offcut and add it to the last cut and if there was anything left add it to the second last cut, and then cut two priority cuts from the next fillet depending on the size needed to compliment my two "grown cuts".

Obviously, if we knew the number of fillets to be processed we could find a better way to utilize them, but we would need to scan the size of say 10 or more fillets ahead (so we could work out a better cutting pattern for them all as a group) and then we wouldn't have "pairs" (125+125 or 110+140 or 120+130 or anything in between) in constant balance from fillet to fillet. The nature of the process is continuous: you want to have portions in balance all the time if possible so that if you stop at any point you don't have too many small cuts or big cuts.

In a test of 10000 450g-900g fillets for 125g target portion size, the way I-Cut software was cutting the cod we were able to get an average yield of 93.5% (pairable portions) and the rest would be for chowder. So far with the last algorithm version, I was able to improve it to an average of 97.6%.

This means over 4% more finished product compared to material for chowder. The difference in value was approximately 4euro per kilo. This plant was processing annually around 350tons of fish (cod alone). 4% * 350000kg * 4euros per kg = 56000euros of a difference every year. That's if the business wouldn't grow.

There are hundreds of plants like this that would benefit from the upgrade. This is simply because these portioners don't take advantage of the fact that a finished product consists of two pieces different in size. They are designed to cut programmed patterns regardless of fillet size.

The solution would find use in any other food industry where in the pack of two there is a tolerance for two pieces to be a slightly different size.

Adam Wandoch
