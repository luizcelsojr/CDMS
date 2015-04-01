//groovybeta
/*
Table Cuisine = opr.scanFilterV({it.type == 'CUISINE'})

Table Ingredient = opr.scanFilterV({it.type == 'INGREDIENT'})//, 1000)

Table IngrPartOf = opr.scanFilterE({it.label == 'ingr_part_of'})//, 10000)

Table Recipe = opr.scanFilterV({it.id >=1000000 && it.id <= 1057690}, 100)

Table OfCuisine = opr.scanFilterE({it.label == 'of_cuisine'}, 1000)

Table CuisineReference = new Table(["id", "name", "ref_id", "ref_name"],
        [
                [4101, 'East-African', 4100, 'African'],
                [4103, 'North-African', 4100, 'African'],
                [4104, 'South-African', 4100, 'African'],
                [4105, 'West-African', 4100, 'African'],
                [4107, 'chinese', 4106, 'China'],
                [4108, 'east_asian', 4163, 'asian'],
                [4110, 'japanese', 4109, 'Japan'],
                [4112, 'korean', 4111, 'Korea'],
                [4114, 'EasternEuropean_Russian', 4113, 'Eastern-Europe'],
                [4116, 'Central_SouthAmerican', 4119, 'South-America'],
                [4117, 'Mexican', 4118, 'mexico'],
                [4130, 'Southwestern', 4162, 'western'],
                [4132, 'Scandinavian', 4131, 'Scandinavia'],
                [4135, 'Indian', 4134, 'India'],
                [4141, 'Thailand', 4140, 'Thai'],
                [4143, 'Vietnamese', 4142, 'Vietnam'],
                [4145, 'Italian', 4146, 'Italy'],
                [4157, 'Germany', 4156, 'German'],
                [4149, 'Spain', 4150, 'Spanish_Portuguese'],
                [4154, 'France', 4155, 'French'],
                [4158, 'Irish', 4161, 'UK-and-Ireland']
        ])



CuisineReference = opr.rename(CuisineReference, [["id", "id_old"]])

CuisineReference = opr.tetaLeftJoin(opr.set(Cuisine, ["it.ref_id = it.id", "it.ref_name = it.name"]), CuisineReference, {it.id == it.id_old  })
CuisineReference = opr.rename(CuisineReference, [["ref_id", "id_n"], ["ref_name", "name"]])

//CuisineReference.print()

//OfCuisine.print()

Table OfCuisineRef = opr.step(Recipe, OfCuisine, Constants.OUTBOUND)
OfCuisineRef = opr.step(OfCuisineRef, CuisineReference, Constants.OUTBOUND)
OfCuisineRef = opr.project(OfCuisineRef, ["id", "id_n", "E_label", "E_name"])
OfCuisineRef = opr.rename(OfCuisineRef, [["E_label", "label"], ["E_name", "name"]])

//OfCuisineRef.print()


//Ingredients in n recipes

Table RareIngr = opr.beta(Ingredient, IngrPartOf, new Table(), 1, {true}, Constants.BOTH, [], ["it.count = 0"], ["it.count = 1"], ["id"], [[aggr:"sum", func:"it.count", as:"count"]], ["current.count = newV.count"])
//Ingredient.print()

RareIngr = opr.select(RareIngr, {it.count && it.count < 10})
RareIngr.orderAsc(["count"])
//RareIngr.print()

IngrPartOf = opr.tetaJoin(IngrPartOf, opr.renameAll(RareIngr, "rare"), {it.id == it.rare_id})

IngrPartOf = opr.project(IngrPartOf, ["id", "id_n", "label"])
IngrPartOf.print()

Table E = opr.union(IngrPartOf, OfCuisineRef)

//Table R = opr.union(RareIngr, Recipe)

//Table CoCusine = opr.beta(Cuisine, E, 2, {true}, Constants.BOTH, [], [], [], ["id", "id_n"], [], [])

Table CoCusine = opr.step(Cuisine, E, Constants.INBOUND)
CoCusine = opr.step(CoCusine, E, Constants.INBOUND)
CoCusine = opr.step(CoCusine, E, Constants.OUTBOUND)
CoCusine = opr.step(CoCusine, E, Constants.OUTBOUND)

CoCusine = opr.project(CoCusine, ["id", "id_n"])
CoCusine = opr.select(CoCusine, {it.id > it.id_n})
CoCusine = opr.unique(CoCusine)

CoCusine.print()

Table result = opr.beta(Cuisine, CoCusine, 3, {true}, Constants.BOTH, ['knows'], ["it.rank = 100.0f"], ["it.rank = it.rank/it.c"], ['id_n'], [[aggr:"sum", func:"it.rank", as:"rank"]], ["it.rank = 0.0f"])

result.print()

*/

Table Cuisine = opr.scanFilterV({it.type == 'CUISINE'})


Table CuisineReference = new Table(["id", "name", "ref_id", "ref_name"],
        [
                [4101, 'East-African', 4100, 'African'],
                [4103, 'North-African', 4100, 'African'],
                [4104, 'South-African', 4100, 'African'],
                [4105, 'West-African', 4100, 'African'],
                [4107, 'chinese', 4106, 'China'],
                [4108, 'east_asian', 4163, 'asian'],
                [4110, 'japanese', 4109, 'Japan'],
                [4112, 'korean', 4111, 'Korea'],
                [4114, 'EasternEuropean_Russian', 4113, 'Eastern-Europe'],
                [4116, 'Central_SouthAmerican', 4119, 'South-America'],
                [4117, 'Mexican', 4118, 'mexico'],
                [4130, 'Southwestern', 4162, 'western'],
                [4132, 'Scandinavian', 4131, 'Scandinavia'],
                [4135, 'Indian', 4134, 'India'],
                [4141, 'Thailand', 4140, 'Thai'],
                [4143, 'Vietnamese', 4142, 'Vietnam'],
                [4145, 'Italian', 4146, 'Italy'],
                [4157, 'Germany', 4156, 'German'],
                [4149, 'Spain', 4150, 'Spanish_Portuguese'],
                [4154, 'France', 4155, 'French'],
                [4158, 'Irish', 4161, 'UK-and-Ireland']
        ])

CuisineReference = opr.rename(CuisineReference, [["id", "id_old"]])

CuisineReference = opr.tetaLeftJoin(opr.set(Cuisine, ["it.ref_id = it.id", "it.ref_name = it.name"]), CuisineReference, {it.id == it.id_old  })
CuisineReference = opr.rename(CuisineReference, [["ref_id", "id_n"], ["ref_name", "name"]])


Table r = opr.scanFilterV({it.type=='CUISINE'})
Table t = opr.beta(r, 2, { true }, Constants.INBOUND, [], ["it.rank = 1.0f"], ["it.rank = 0.8* it.rank/c"], ['id_n', 'id'], [[aggr: "sum", func: "it.rank", as: "rank"]], ["current.rank = newV.rank + current.rank"])
t = opr.select(t, {it.id_n == 1283})
t.orderDesc('rank')
t.print(30)
t = opr.renameAll(opr.project(t, ["id", "name", "rank"]), "c")
t.print(30)
t = opr.tetaJoin(t, CuisineReference, {it.id == it.c_id})

t.print(30)

